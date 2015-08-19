package cn.jeesoft.core.resolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import cn.jeesoft.core.model.PagerModel;
import cn.jeesoft.core.utils.StringUtils;

/**
 * 数据绑定方法
 */
@SuppressWarnings("unchecked")
public class ArgumentsResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
//		return parameter.hasParameterAnnotation(FormBean.class);
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ServletRequest servletRequest = (ServletRequest) webRequest.getNativeRequest();

		PropertyValues pvs = null;
		Object bindObject = null;

		String prefix = getPrefix(parameter);

		Class<?> paramType = parameter.getParameterType();
		
		if (Collection.class.isAssignableFrom(paramType) || paramType.isArray()) {
			Class<?> genericClass = null;
			if (paramType.isArray()) {
				genericClass = paramType.getComponentType();
			} else {
				Type type = parameter.getGenericParameterType();
				if (type instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) type;
					genericClass = (Class<?>) pt.getActualTypeArguments()[0];
				}
			}
			if (genericClass != null) {
				Map<String, Object> mappedValues = createMappedValues(genericClass, webRequest, parameter, prefix);
				if (!mappedValues.isEmpty()) {
					List<Object> targetObject = new ArrayList<Object>(mappedValues.values());
					WebDataBinder binder = new WebDataBinder(null);
					bindObject = binder.convertIfNecessary(targetObject, paramType);
				}
			}
		} else if (Map.class.isAssignableFrom(paramType)) {
			Class<?> genericClass = null;
			Type type = parameter.getGenericParameterType();
			if (type instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type;
				genericClass = (Class<?>) pt.getActualTypeArguments()[1];
			}

			if (genericClass != null) {
				Map<String, Object> mappedValues = createMappedValues(genericClass, webRequest, parameter, prefix);
				if (!mappedValues.isEmpty()) {
					Map<String, Object> targetObject = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : mappedValues.entrySet()) {
						String key = entry.getKey();
						key = key.substring(key.indexOf("['") + 2, key.indexOf("']"));
						targetObject.put(key, entry.getValue());
					}
					WebDataBinder binder = new WebDataBinder(null);
					bindObject = binder.convertIfNecessary(targetObject, paramType);
				}
			}

		} else {
			if (PagerModel.class.isAssignableFrom(paramType)) {
				if (StringUtils.isEmpty(prefix)) {
					prefix = "pager";
				}
			}
			
			pvs = new ServletRequestParameterPropertyValues(servletRequest, prefix, separator);

			bindObject = convertIfDomainClass(webRequest, pvs, paramType, prefix);

			if (null == bindObject) {
				bindObject = BeanUtils.instantiateClass(paramType);
			}
			
			WebDataBinder binder = new WebDataBinder(bindObject, prefix);
            binder.registerCustomEditor(Date.class, new DateEditorSupport());
			
			// binder.initDirectFieldAccess();
			binder.bind(pvs);
		}

		return bindObject;
	}

	/**
	 * 获取参数前缀
	 * @param parameter
	 */
	private static String getPrefix(MethodParameter parameter) {
		FormBean formBean = parameter.getParameterAnnotation(FormBean.class);
		if (formBean != null) {
			return formBean.value();
		}
		return null;
	}
	
	
	
	
	private static final String DEFAULT_SEPARATOR = ".";
	private String separator = DEFAULT_SEPARATOR;

	/**
	 * Setter to configure the separator between prefix and actual property value. Defaults to
	 * {@link #DEFAULT_SEPARATOR}.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
	}

	/*
	 * 生成指定前缀的数据Map
	 */
	private Map<String, Object> createMappedValues(Class<?> genericClass, NativeWebRequest webRequest,
			MethodParameter parameter, String prefix) {
		ServletRequest servletRequest = (ServletRequest) webRequest.getNativeRequest();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		WebDataBinder binderHelper = new WebDataBinder(null);
		// 将数组提取为一个一个的KEY，这里是集合必须要有prefix + '['
		Set<String> keySet = getSortedKeySet(servletRequest, prefix + '[');
		for (String key : keySet) {
			Object genericObj = null;
			if (key.endsWith(separator)) {
				ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest,
						key, StringUtils.EMPTY);

				String realKey = key.substring(0, key.length() - 1);
				genericObj = convertIfDomainClass(webRequest, pvs, genericClass, realKey);
				if (null == genericObj) {
					genericObj = BeanUtils.instantiateClass(genericClass);
				}

				WebDataBinder objectBinder = new WebDataBinder(genericObj, realKey);
				objectBinder.bind(pvs);
			} else {
				Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, key);
				if (!paramValues.isEmpty()) {
					if (Collection.class.isAssignableFrom(genericClass)) {
						genericObj = binderHelper.convertIfNecessary(paramValues.values(), genericClass);
					} else {
						genericObj = binderHelper.convertIfNecessary(paramValues.values().iterator().next(),
								genericClass);
					}
				}
			}
			if (genericObj != null) {
				resultMap.put(key, genericObj);
			}
		}
		return resultMap;
	}

	/*
	 * 获取指定前缀的KEY值
	 */
	private Set<String> getSortedKeySet(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(prefix, "Prefix must not be null");
		Enumeration<String> paramNames = request.getParameterNames();
		Set<String> keySet = new TreeSet<String>(ComparatorImpl.INSTANCE);
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(prefix)) {
				String key = paramName;
				int lastScopeIndex = paramName.indexOf(']');
				int firstSeparator = paramName.indexOf(separator);
				if (firstSeparator > lastScopeIndex) {
					// 这里把separator也加上，用来判断是简单数据类型还是复杂类型
					key = paramName.substring(0, firstSeparator + 1);
				}
				if (!keySet.contains(key)) {
					keySet.add(key);
				}
			}
		}
		return keySet;
	}

	private static final class ComparatorImpl implements Comparator<String> {
		public static final ComparatorImpl INSTANCE = new ComparatorImpl();

		@Override
		public int compare(String left, String right) {
			int lengthCompare = left.length() - right.length();
			return lengthCompare != 0 ? lengthCompare : left.compareTo(right);
		}
	}

	/*
	 * 如果是Domain Class，则根据是否有ID属性来自动查询实体数据，再行绑定
	 */
	private Object convertIfDomainClass(WebRequest webRequest, PropertyValues pvs, Class<?> paramType, String prefix) {
//		// 如果参数是Domain Class，则看看是否有ID，有就根据ID读取数据
//		if (Persistable.class.isAssignableFrom(paramType)) {
//			PropertyValue idValue = pvs.getPropertyValue("id");
//			if (null != idValue) {
//				String idString = (String) idValue.getValue();
//				if (StringUtils.isNotEmpty(idString)) {
//					WebDataBinder binder = new WebDataBinder(null, prefix + separator + "id");
//					if (webBindingInitializer != null) {
//						webBindingInitializer.initBinder(binder, webRequest);
//					}
//					return binder.convertIfNecessary(idString, paramType);
//				}
//			}
//		}
		return null;
	}

//	/**
//	 * @param tokenValidation
//	 *            the tokenValidation to set
//	 */
//	public void setTokenValidation(TokenValidation tokenValidation) {
//		this.tokenValidation = tokenValidation;
//	}
	
}
