package com.xinding.travel.constant;

/**
 * @ClassName: SystemConstant
 * @Description: 系统常量类
 * @author workh
 * @date 2015-5-7 上午10:46:54
 * 
 */
public class SystemConstant {
    // 活动是否被删除（2：正常，0：删除）
    public static final Integer EVENT_STATUS_NORMAL = 2;
    // 活动是否被删除（2：正常，0：删除）
    public static final Integer EVENT_STATUS_DELETE = 0;
    public static final Integer BOOK_FLAG_SUCCESS = 2;
    public static final Integer BOOK_FLAG_ING = 1;
    public static final Integer BOOK_FLAG_FAIL = 0;

    // 版本标识 1 android 2 ios 3 windows phone
    public static final Integer VERSION_TYPE_ANDROID = 1;

    public static final Integer VERSION_TYPE_IOS = 2;

    public static final Integer VERSION_TYPE_PHONE = 3;
    // 上传文件类型 1 image，2 video
    public static final String FILE_TYPE_IMAGE = "1";
    public static final String FILE_TYPE_VIDEO = "2";
    public static final String FILE_TYPE_MUSIC = "3";
    public static final Integer FILE_LIMIT_SIZE = 102400;
    public static final Integer MUSIC_LIMIT_SIZE = 20971520;
    public static final Long IMG_LIMIT_SIZE=512000L;

    //音乐文件标志
    public static final String MUSIC_TYPE = "3";
    // 视频文件标志
    public static final String VIDEO_TYPE = "2";
    // 图片文件标志
    public static final String IMG_TYPE = "1";
    // 大师上传标志
    public static final String FROM_MASTER = "1";
    // 大师路径
    public static final String MASTER_PATH = "master";
    // 活动上传标志
    public static final String FROM_EVENT = "2";
    // 活动路径
    public static final String EVENT_PATH = "event";
    // 场馆上传标志
    public static final String FROM_VENUES = "3";
    // 场馆路径
    public static final String VENUES_PATH = "venues";
    // 会员上传标志
    public static final String FROM_MEMBER = "4";
    // 会员路径
    public static final String MEMBER_PATH = "member";
    // 画廊展品、衍生品、红包banner上传标志
    public static final String FROM_OTHER = "5";
    // 画廊展品、衍生品、红包banner路径
    public static final String OTHER_PATH = "others";
    //景点上传标志
    public static final String FROM_SPOT = "6";
    //景点路径
    public static final String SPOT_PATH = "spot";
    //商户上传标志
    public static final String FROM_STORE = "7";
    //商户路径
    public static final String STORE_PATH = "store";
    //公共服务上传标志
    public static final String FROM_PUBLICSERVICE = "8";
    //公共服务路径
    public static final String PUBLICSERVICE_PATH = "publicservice";
    //广告上传标志
    public static final String FROM_AD = "9";
    //广告路径
    public static final String AD_PATH = "ad";
    //路线上传标志
    public static final String FROM_LINE = "10";
    //路线路径
    public static final String LINE_PATH = "line";
    //区域上传标志
    public static final String FROM_REGION = "11";
    //区域路径
    public static final String REGION_PATH = "region";
    //图片路径
    public static final String IMG_PATH = "img";
    //视频路径
    public static final String VIDEO_PATH = "video";
    //音频路径
    public static final String MUSIC_PATH = "music";
    
	// 移动端二维码存放路径
	public static final String QRCODE_PATH = "qrcode";
    // ***********************************系统配置项key开始**************************************//
    // 场馆推荐设置
    public static final String SYSCONFIG_KEY_VENUES_RECOMMEND = "venuesConfig";

    // 公益活动推荐设置
    public static final String SYSCONFIG_KEY_SOCIAL_EVENT_RECOMMEND = "socialEventConfig";
    // ***********************************系统配置项key开始**************************************//
    public static final String HESSION_URL = "https://www.wondersculture.com/why-tools/hessian/solrService";
    public static final String SMS_BONUS_URL = "https://www.wondersculture.com/why-web/doleBonus/FUYC89R?GHCV=";
    public static final String key = "1115111211131114";
    //官方回复人
    public static final String ANSWER_NAME = "爬虫旅游客服";
    
	// 旅游云
	public static String lyy_CODE = "lyy";
}
