package cn.jeesoft.core.oscache;

import org.springframework.beans.factory.annotation.Autowired;

import cn.jeesoft.core.SystemManager;
import cn.jeesoft.mvc.bean.ManageSetting;
import cn.jeesoft.mvc.services.ManageSettingService;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 * @author huangf
 */
public class ManageCache {
	
	/**
	 * manage后台
	 */
//    @Resource(name = "orderServiceManage")
//	private OrderService orderService;
//    @Resource(name = "productServiceManage")
//	private ProductService productService;
//    @Resource(name = "commentServiceManage")
//	private CommentService commentService;
//    @Resource(name = "areaServiceManage")
//	private AreaService areaService;
//    @Resource(name = "taskServiceManage")
//	private TaskService taskService;

//	public void setTaskService(TaskService taskService) {
//		this.taskService = taskService;
//	}
//
//	public void setAreaService(AreaService areaService) {
//		this.areaService = areaService;
//	}
//
//	public OrderService getOrderService() {
//		return orderService;
//	}
//
//	public void setOrderService(OrderService orderService) {
//		this.orderService = orderService;
//	}
//
//	public ProductService getProductService() {
//		return productService;
//	}
//
//	public void setProductService(ProductService productService) {
//		this.productService = productService;
//	}
//
//	public CommentService getCommentService() {
//		return commentService;
//	}
//
//	public void setCommentService(CommentService commentService) {
//		this.commentService = commentService;
//	}

    /**
     * manage后台
     */
    @Autowired
    private ManageSettingService systemSettingService;
    
    /**
     * 加载系统配置信息
     */
    public void loadSystemSetting() {
        SystemManager.manageSetting = systemSettingService.selectOne(new ManageSetting());
        if (SystemManager.manageSetting == null) {
            throw new NullPointerException("未设置本地环境变量，请管理员在后台进行设置");
        }
    }
    
    
    /**
	 * 加载订单报表
	 */
	public void loadOrdersReport(){
//		SystemManager.ordersReport = orderService.loadOrdersReport();
//		if(SystemManager.ordersReport==null){
//			SystemManager.ordersReport = new OrdersReport();
//		}
//		//加载缺货商品数
//		SystemManager.ordersReport.setOutOfStockProductCount(productService.selectOutOfStockProductCount());
//
//		//加载吐槽评论数
//		SystemManager.ordersReport.setNotReplyCommentCount(commentService.selectNotReplyCount());
//		
//		logger.error("SystemManager.ordersReport = " + SystemManager.ordersReport.toString());
	}
	
//	/**
//	 * 加载省市区数据
//	 */
//	private void loadArea(){
//		logger.error("loadArea...");
//		Area area = new Area();
//		area.setPcode("0");
//		List<Area> rootData = areaService.selectList(area);
//		if(rootData==null){
//			return ;
//		}
//		
//		for(int i=0;i<rootData.size();i++){
//			Area item = rootData.get(i);
//			getAreaByDigui2(item);
//		}
//		
//		Map<String, Area> map = new TreeMap<String, Area>();
//		for(int i=0;i<rootData.size();i++){
//			Area item = rootData.get(i);
//			map.put(item.getCode(), item);
//		}
//		
//		SystemManager.areaMap = map;
//		
////		logger.error("SystemManager.areaMap=="+SystemManager.areaMap);
//		
//		String json = JSON.toJSONString(SystemManager.areaMap);
////		logger.error("json="+json);
//		try {
//			//写到文件
//			File file = new File("__area.txt");
//			logger.error(file.getAbsolutePath());
//			FileUtils.writeStringToFile(new File("__area.json"), json, "utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	private void readJsonArea(){
//		long start = System.currentTimeMillis();
//		try {
//			String path = ManageCache.class.getResource("/").getPath();
//			logger.error("path = " + path);
//			File file = new File(path + "__area.json");
//			logger.error(file.getAbsolutePath());
//			List<String> list = FileUtils.readLines(file, "utf-8");
//			logger.error("list.size()="+list.size());
//			
//			SystemManager.areaMap = JSON.parseObject(list.get(0),new TypeReference<Map<String,Area>>(){});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		logger.error("readJsonArea time = " + (System.currentTimeMillis() - start));
//	}
	
	/**
	 * 加载定时任务列表
	 */
	public void loadTask(){
//		List<Task> list = taskService.selectList(new Task());
//		if(list!=null){
//			TaskManager.taskPool.clear();
//			for(int i=0;i<list.size();i++){
//				Task item = list.get(i);
//				TaskManager.taskPool.put(item.getCode(),item);
//			}
//		}
	}
	
	/**
	 * 加载全部的缓存数据
	 * @throws Exception 
	 */
	public void loadAllCache() throws Exception {
		System.out.println("ManageCache.loadAllCache...");
		loadOrdersReport();
//		readJsonArea();
		loadTask();
        loadSystemSetting();
        System.out.println("后台缓存加载完毕!");
	}

}
