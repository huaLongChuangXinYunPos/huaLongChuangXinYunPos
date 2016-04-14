package com.mall.hlcloundposproject;

/**
 * com.hlrj.hlcloundpos
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-21
 */
/**
 * 配置所有    软件版本  信息  和网络访问url
 */
public final class Configs {
	
	/**
	 * 用于保存共享参数  对象   共享参数  文件
	 */
	public static final String APP_NAME = "hlrjPosClound";
	
	/**
	 * 获取 服务器地址：192.168.3.200:1238
	 */
	public static final String SERVER_BASE_URL = "http://192.168.3.200:1238/AppServer/";
	
	/**
	 * 根据    手机硬件  序列号  获取   pos机标识：
	 */
	public static final String GET_POS_Id = "GetPosID.aspx?action=RQPOS&AppCode=%s";
	
	/**
	 * 用户账户登录访问  服务器地址：
	 */
	public static final String LOGIN_USER = SERVER_BASE_URL +
			"GetUserInf.aspx?action=RQONE&userName=%s";
	
	/**
	 * 定义   登录异步任务的  访问标识：   UserLoginTask的标识
	 */
	public static final int USER_LOGIN_TASK_AUTHORITY = 0;
	
	/**
	 * 定义     访问  服务器  获取   区间内商品信息的   异步任务标识   ：GetGoodsInfoAsyncTask 
	 */
	public static final int GET_GOODS_INFO_AUTHORITY = 1;
	
	/**
	 * 定义    获取    所有用户信息的任务标识：
	 */
	public static final int GET_ALL_USER_DATA_AUTHORITY = 2;
	
	/**
	 * 定义获取    所有商品信息    更新商品数据库的  异步任务标识
	 */
	public static final int UPDATE_ALL_GOOD_DB_AUTHORITY = 3;
	
	/**
	 * 定义  获取  所有特价商品   异步任务标识：
	 */
	public static final int UPDATE_GOODS_SPECIAL_AUTHORITY = 4;
	
	/**
	 * 定义      打开    扫描Activity   请求数据的请求码：
	 */
	public static final int TO_SCAN_CODEBAR_RESULT_CODE = 14;
	
	/**
	 * 定义  获取 所有结算方式 的    fragment的标识
	 */
	public static final int GET_CALCULATE_WAY_AUTHORITY = 5;  
	
	/**
	 * 定义   vipFragment的回调标识
	 */
	public static final int VIP_FRAGMENT_QUHORITY = 6;
	
	/**
	 * 获取  结算 结果的    异步标识
	 */
	public static final int GET_PAY_CALCULATE_RESULT_AUTHORITY = 7;
	
	/**
	 * 查询单条   数据信息
	 * http://192.168.3.199:1234/AppServer/GetGoodsList.aspx?action=RQONE&cBarcode=6921744700275
	 */
	public static String QUERY_ONE_GOODS_DATA = "GetGoodsList.aspx?action=RQONE&cBarcode=%s";
	
	/**
	 * 查询所有     用户信息    http://192.168.3.199:1234/AppServer/GetUserInf.aspx?action=RQAll
	 */
	public static String QUERY_ALL_USERS_DATA = "GetUserInf.aspx?action=RQAll";
	
	/**
	 * 更新  所有商品库信息     从服务器获取数据
	 * http://192.168.3.199:1234/AppServer/GetGoodsList.aspx?action=RQPOS
	 */
	public static String QUERY_ALL_GOODS_INFO = "GetGoodsList.aspx?action=RQPOS&Bgn=%s&End=%s";
	
	/**
	 * 更新商品信息库中   特价表中商品
	 */
	public static String QUERY_ALL_SPECIAL_GOODS ="GetPloyOfSale.aspx?action=RQPloy";
	
	/**
	 * 查询    vip会员信息：
	 */
	public static String QUERY_VIP_INFO = "GetVip.aspx?action=RQVIP&vipNo=%s";
	
	/**
	 * 查询  vip商品信息的    链接   
	 */
	public static final String QUERY_VIP_GOODS_DATA = "GetGoodsList.aspx?action=RQVIP";
	
	/**
	 * 获取  所有 特价商品信息的  连接：
	 */
	public static final String QUERY_SPECIAL_GOODS = "GetPloyOfSale.aspx?action=RQPloy";
	
	
	/**
	 * 获取  所有的计算方式：
	 */
	public static final String GET_PAY_CALCULATE_WAY = "GetJsType.aspx?action=RQJSTYPR";
	
	/**
	 * 修改密码  和账号  发送至服务器：
	 */
	public static final String UPDATE_PWD_TO_SERVER ="GetUserInf.aspx?action=RQUPDATE&" +
			"userName=%s&newPass=%s";
	
	/**
	 * 将   会员积分信息发送到    服务器：
	 */
	public static final String UPDATE_VIP_SCORE = "GetVip.aspx?action=RQSCO&vipNo=%s&fValue=%s";

	/**
	 * 客退回回调    标识  backFragment
	 */
	public static final int CONSUME_BACK_FRAGMENT_AUTHORITY = 2;

	/**
	 * 刷新   单个商品数量的    回调标识
	 */
	public static final int UPDATE_FRAGMENT_AMOUNT = 1;

	/**
	 * 用户 选择挂单表的 回退SelectFormTempFragment
	 */
	public static final int SELECT_TEMP_FRAGMENT_AUTHORITY = 0;

	/**
	 * 代表   共享参数中pos机的id
	 */
	public static final String POS_ID = "posid";
	
	public static final int SCANNIN_ALI_REQUEST_CODE = 13;//支付宝扫描返回码
	
	public static final int SCANNIN_WE_CHAT_REQUEST_CODE = 30; //微信扫描返回码
	
	/**
	 * 结算  完毕将数据发送至服务器：
	 */
	public static final String SELLED_DATA_TO_SERVER = "http://192.168.3.200:1238/AppPosServer.asmx/RetPosDetail";
	
	
	public static final int QUERY_ACTIVITY_RESULT_CODE = 34;
	
	public static final int QUERY_ACTIVITY_REQUEST_CODE = 36;
	
	/**
	 * 添加    客户信息的回调标识
	 */
	public static final int ADD_CONSUMER_INFO_SUCCESS_AUTHORITY = 31;
	
	public static final int CONSUMER_INFO_REQUEST_AUTHORITY = 23;
	
	public static final int CONSUMER_INFO_RESULT_AUTHORITY = 28;
}
