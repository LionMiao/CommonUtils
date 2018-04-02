/**
 *****************************************************************************
 * Copyright (C) 2005-2014 UCWEB Corporation. All rights reserved
 * File        : ShellFeatureConfig.java
 *
 * Description : Providing various configuration items for browser shell features.
 * Author      : liaofei@ucweb.com
 *****************************************************************************
 */
package com.ehang.commonutils.config;

public final class ShellFeatureConfig {
    /**
     * 是否国际版
     */
    public static final boolean IS_INTERNATIONAL_VERSION = false;

    /**
     * 动态加载dex
     */
    public static final boolean ENABLE_DYNAMIC_DEX = false;
    /**
     * 动态加载dex的子选项
     * 是否优先加载/data/local/tmp/下so库或dex.
     * XXX: 开发专用, 打包平台打包默认为false, 本地打包为true.
     */
    public static final boolean ENABLE_PRIORITY_LOAD_LIBS_FROM_TMP = true;
    
    /**
     * 启动时加载所有的dex，极速编译无接口和工厂，开发专用
     * 和动态加载dex只能二选一
     */
    public static final boolean ENABLE_PRELOAD_DEX = true;
    
    public static final boolean ENABLE_FPS = false;
    public static final boolean ENABLE_JAVA_HEAP = false;
    public static final boolean ENABLE_HOMEPAGE_HARDCODE_FEATURE = false;
    public static final boolean DISABLE_SHELL_FEATURE = false;
    public static final boolean DISABLE_BARCODE_SIRI_FEATURE = false;
    public static final boolean ENABLE_FULLSCREEN_FEATURE = false;

    /**
     * 发送桌面快捷方式时，要不要弹出提示，有些桌面在你发送后，自动会帮你弹出一个提示，而我们uc也弹，就非常难看，加这个开关，默认是弹出提示：
     */
    public static final boolean ENABLE_SEND_TO_DESK_PROMPT = true;
    /**
     * 松鼠加载页面不显示版本信息，主要是给一些厂商预装时，厂商怕用户骂他们的uc不是最新的，所以把版本号干掉,默认是显示：
     */
    public static final boolean ENABLE_SHOW_VERSION = true;
    /**
     * 对话框　uc网盘是否开启
     */
    public static final boolean ENABLE_UCNETDISK = false;
    /**
     * 是否开启插件签名校验
     */
    public static final boolean ENABLE_ADDON_SIGNATURE_CHECK = true;
    /**
     * 是否是移动定制版
     */
    public static final boolean ENABLE_CMCC = false;
    /**
     * 是否上传了启动画面，会在resources\yz\preset\replace_preset.py脚本中打包替换
     */
    public static final boolean UPLOAD_SPLASH_LOGO = false;
    /**
     * 是否开启灰度验证
     */
    public static final boolean ENABLE_GRAY_VERIFICATION = false;
    /**
     * 是否开启极速启动
     */
    public static final boolean ENABLE_FAST_START = false;
    /**
     * 是否开启严格模式
     */
    public static final boolean ENABLE_STRICTMODE = false;
    /**
     * 是否开启百度video
     */
    public static final boolean ENABLE_BAIDU_VIDEO = true;
    /**
     * 是否开启将toolbar的左右互换的开关
     */
    public static final boolean ENABLE_TOOLBAR_BUTTON_EXCHANGE = false;

    /**
     * for performance monitor
     */
    public static final boolean ENABLE_OUTPUT_BENCHMARK_FOR_DEV = false;
    public static final boolean ENABLE_OUTPUT_BENCHMARK_MEMINFO = false;
    public static final boolean ENABLE_UI_AUTOTEST = false;
    /**
     * 创建桌面快捷方式和二维码快捷方式、左屏搜索快捷方式，默认允许创建
     */
    public static final boolean ENABLE_CREATE_SHORTCUT = true;

    /**
     * For Qualcomm battery mode
     */
    public static final boolean ENABLE_QUALCOMM_QDR = false;
    public static final boolean ENABLE_PP_INTENAL_VERSION = false;
    /**
     * 允许使用小说快搜测试环境
     */
    public static final boolean ENABLE_NOVEL_TEST = true;

    /**
     * 打包平台用于控制信息搜集级别的开关
     * 4. 即对应发布级别，日志最少
     * 3. 灰度级别
     * 2. 内测级别
     * 1. 迭代包级别
     * 0. 调试包
     */
    private static final int DEBUG_INFO_OUTPUT_LEVEL = 0;

    /**
     * 是否上传崩溃日志
     */
    public static final boolean CRASH_UPLOAD_FLAG;
    /**
     * 崩溃日志是否加密
     */
    public static final boolean CRASH_LOG_ENCRYPT;
    /**
     * 崩溃日志是否输出到Logcat
     */
    public static final boolean ENABLE_DEBUG_CRASH;
    /**
     * Logcat最大条数
     */
    public static final String LOGCAT_LINES_LIMIT;
    /**
     * 崩溃日志最大保留条数
     */
    public static final int CRASH_LOG_MAX_COUNT;
    /**
     * 版本级别（Release、Gray、Closed、Iterative、Develop）
     */
    public static final String VER_DESCRIPTOR;

    static {
        if (ShellFeatureConfig.DEBUG_INFO_OUTPUT_LEVEL == 4) {
            ENABLE_DEBUG_CRASH = false;
            CRASH_UPLOAD_FLAG = true;
            CRASH_LOG_ENCRYPT = true;
            CRASH_LOG_MAX_COUNT = 10;
            LOGCAT_LINES_LIMIT = "500";
            VER_DESCRIPTOR = "4-Release";
        } else if (ShellFeatureConfig.DEBUG_INFO_OUTPUT_LEVEL == 3) {
            ENABLE_DEBUG_CRASH = false;
            CRASH_UPLOAD_FLAG = true;
            CRASH_LOG_ENCRYPT = true;
            CRASH_LOG_MAX_COUNT = 10;
            LOGCAT_LINES_LIMIT = "1000";
            VER_DESCRIPTOR = "3-Gray";
        } else if (ShellFeatureConfig.DEBUG_INFO_OUTPUT_LEVEL == 2) {
            ENABLE_DEBUG_CRASH = false;
            CRASH_UPLOAD_FLAG = true;
            CRASH_LOG_ENCRYPT = true;
            CRASH_LOG_MAX_COUNT = 10;
            LOGCAT_LINES_LIMIT = "2000";
            VER_DESCRIPTOR = "2-Closed";
        } else if (ShellFeatureConfig.DEBUG_INFO_OUTPUT_LEVEL == 1) {
            ENABLE_DEBUG_CRASH = true;
            CRASH_UPLOAD_FLAG = true;
            CRASH_LOG_ENCRYPT = false;
            CRASH_LOG_MAX_COUNT = 1000;
            LOGCAT_LINES_LIMIT = "3000";
            VER_DESCRIPTOR = "1-Iterative";
        } else { // for developers
            ENABLE_DEBUG_CRASH = true;
            CRASH_UPLOAD_FLAG = false;
            CRASH_LOG_ENCRYPT = false;
            CRASH_LOG_MAX_COUNT = 1000;
            LOGCAT_LINES_LIMIT = "3000";
            VER_DESCRIPTOR = "0-Develop";
        }
    }

    //为了不对外暴露DEBUG_INFO_OUTPUT_LEVEL，封装以下方法
    
    /**
     * 灰度包
     * @return
     */
    public static final boolean isGrayVersion() { return DEBUG_INFO_OUTPUT_LEVEL == 3; }
    
    /**
     * 非正式版本
     * @return
     */
    public static final boolean isUnofficialVersion() { return DEBUG_INFO_OUTPUT_LEVEL <= 3; }
    /**
     * 提测版本
     * @return
     */
    public static final boolean isIterativeVersion() { return DEBUG_INFO_OUTPUT_LEVEL <= 1; }
    /**
     * 研发版本
     * @return
     */
    public static final boolean isDevelopVersion() { return DEBUG_INFO_OUTPUT_LEVEL == 0; }

    /**
     * 是否默认勾选“参加用户体验改进计划”
     * true: 默认勾选
     * false: 默认不勾选
     */
    public static final boolean JOIN_IN_USER_PLAN_CHECKED_STATE = true;
    /**
     * 打包平台用于标记是否预装包
     */
    public static final boolean ENABLE_YZ_VERSION = false;
    /**
     * 用户体验统计开关（from 负体验统计需求）
     */
    public static final boolean ENABLE_USER_EXPERIENCE_STATS = true;
    /**
     * 是否允许js设置cd上传参数, 仅在调试时打开
     */
    public static final boolean ENABLE_JS_SET_CD_PARAM = false;
    /**
     * 锁imei总开关  这么命名是因为要和打包平台对应。同时这个变量可以用于在别的地方控制关闭相关的逻辑
     */
    public static final boolean ENABLE_LOCKIMEI = false;
    /**
     * 增加云同步开关
     */
    public static final boolean ENABLE_CLOUD_SYNC = true;

    public static final boolean ENABLE_TEST_T1 = false;

    /**
     * 在下载应用界面是否有热门资源这一tab，下载管理中是否有应用更新这一折叠栏
     */
    public static final boolean ENABLE_HOT_RESOURCE = true;

    /**
     * 显示免责声明对话框的类型
     * <li>个人版 = 0
     * <li>预装普通包 = 1
     * <li>电信预装包 = 2
     * <li>华为预装包 = 3
     * <li>移动包  = 4
     */
    public static final int LEGAL_INFO_DIALOG_TYPE = 0;

    /**
    * 免责声明提示框上的“不再提醒”默认是否勾选
    * true: 默认勾选
    * false: 默认不勾选
    */
    public static final boolean LEGAL_INFO_DIALOG_CHECKED_DEFAULT = false;

    /**
     * 是否在关于中显示升级按钮
     */
    public static final boolean ENABLE_UCMOBILE_UPDATE = true;

    /**
     * 控制弹出内存不足提示的内存值,  值<=0时不弹提示
     */
    public static final long MIN_AVAILABLE_INTERNAL_MEMORY = 0;

    /**
     * 是否弹出设置默认浏览器和在设置界面显示设置默认浏览器的按钮, true则弹出且显示
     */
    public static final boolean ENABLE_SET_DEFAULT_BROWSER = true;

    /**
     * 中兴升级提示 false：不使用 true：使用
     */
    public static final boolean ENABLE_ZTE_UPGRADETIP = false;

    /**
     *  FIXME dengzr: 正式发布之前暂时屏蔽新手引导 (国际版默认为false)
     */
    public static final boolean ENABLE_NEWFUNCTION_GUIDE = true;

    /**
     * 是否打开“发送至桌面”功能  true：打开 false：屏蔽 默认打开
     */
    public static final boolean ENABLE_SEND_TO_DESKTOP = true;

    /**
     * 是否启用启动资源预警，此开关不受打包平台控制
     * Only debug package mode, ResourceMonitor does work.
     */
    public static final boolean DEBUG_INIT_RESOURCE_LIST = false && isDevelopVersion();

    /**
     * 退出浏览器时是否进行kill process。默认会kill process。
     */
    public static final boolean ENABLE_KILL_PROCESS_ON_EXIT = true;
    /**
     *  打开时会将卡顿的日志输出到/sdcard/ui/目录下的fps.txt和consume.txt
     *  当开关打开，会在uc退出的时候写文件，所以会对退出速度有影响
     */
    public static final boolean ENABLE_OUTPUT_SMOOTH_LOG_2_SDCARD = false;
    /**
     * 淘宝闪屏开关
     */
    public static final boolean ENABLE_TAOBAO_GUIDE = false;
    /**
     * 淘宝闪屏开关 开时,下面的url才会生效
     */
    public final static String TAOBAO_URL = "http://m.quecai.com/market/activity/newMemberActivity.php";

    /**
     * 是否允许通过INTENT的方式进行调试
     */
    public static final boolean ENABLE_INTENT_DEBUG = isUnofficialVersion();

    /**
     * 是否启用手机报bug工具(啄虫精灵)  打开：true 关闭：false 默认：false
     */
    public static final boolean ENABLE_BUGS_REPORT_TOOL = false;

    /**
     * 导航同步功能，个人版为true，国际版为false
     */
    public static final boolean ENABLE_NAVISYNC = true;

    /**
     * 是否开启国际版内测
     */
    public static final boolean ENABLE_PRIVATE_TEST_INTER = false;

    /**
     * 首页二期搜索需求开关
     */
    public static final boolean ENABLE_NEW_SEARCH_FEATURE = !IS_INTERNATIONAL_VERSION;

    /**
     * 是否允许国际折叠栏优化功能
     */
    public final static boolean ENABLE_INTER_FOLDINGBAR_OPT = IS_INTERNATIONAL_VERSION;

    /**
     * 是否启用下滑呼出地址栏功能
     */
    public final static boolean ENABLE_SCROLL_ADDRESS_BAR = true;

    /**
     * 是否使用新的卡片式多窗口管理方案
     */
    public final static boolean ENABLE_NEW_MULTI_WINDOW_MANAGER_UI = true;

    /**
     * 是否使用滑动工具栏快速切换窗口
     */
    public final static boolean ENABLE_FAST_SWITCH_WINDOW = IS_INTERNATIONAL_VERSION;

    /**
     * 主线程过程输出开关
     */
    public static final boolean ENABLE_LOOPER_LOG = false;

    /**
     * 主线程输出卡顿当时的进程堆栈
     */
    public static final boolean ENABLE_LOOPER_DUMP_TRACES = false;

    /**
     * 允许上传主线程输出卡顿当时的进程堆栈
     */
    public static final boolean ENABLE_UPLOAD_LOOPER_DUMP_TRACES = false;

    /**
     * 主线程卡顿信息输出开关，默认"0001"，只输出logcat
     * bit 0 : LogPrinter
     * bit 1 : FilePrinter
     * bit 2 : AnrPrinter
     * bit 3 : StatsPrinter
     */
    public static final String LOOPER_HOOK_FLAG = "0001";

    /**
     * 主线程卡顿信息输出阈值，单位ms，默认只输出loop耗时>100ms的过程
     */
    public static final int LOOPER_HOOK_INTERVAL = 100;

    /**
     * 允许打印并将IO信息导出到文件
     */
    public static final boolean ENABLE_DUMP_IO_LOG = false;
     /**
     * 首次安装uc后，再过多少天去检查uc有新版本发布, 预装开关
     */
    public static final int UPGRADE_DELAY_DAYS = 0;

    /**
     * 是否增加统计上传的数量, 方便测试
     */
    public static final boolean INCREASE_AMOUNT_OF_STAT_DATA  = false;

    /**
     * 是否用于白盒测试
     */
    public static final boolean ENABLE_WHITEBOX_TEST = false;

    /**
     * 是否使用U3内核。当为false时，浏览器将切换到使用Android提供的WebKit内核。
     */
    public static final boolean USE_U3_CORE = true;

    /**
     * 是否开启天气Widget
     */
    public static final boolean ENABLE_WEATHER_WIDGET = !IS_INTERNATIONAL_VERSION;

    /**
     * 是否开启“我的视频”相关功能
     */
    public static final boolean ENABLE_MY_VIDEO = !IS_INTERNATIONAL_VERSION;

    /**
     * 是否使用分享面板进行分享
     */
    public static final boolean ENABLE_SHARE_PLATFORM = !IS_INTERNATIONAL_VERSION;

    /**
     * 是否使用AppsFlyer
     */
    public static final boolean ENABLE_APPS_FLYER = false;

    /**
     * 是否开启assert
     */
    public static final boolean ENABLE_UCASSERT = true;

    /**
     * 宏开关，是否打开小说功能
     */
    public static final boolean ENABLE_NOVEL = true;

    /**
     * 宏开关，是否打开企业轻应用功能
     */
    public static final boolean ENABLE_LIGHT_APP = true;

    /**
     * 宏开关，是否打开快速搜索
     */
    public static final boolean ENABLE_QUICK_SEARCH = true;

    /**
     * 宏开关，是否保留微信SDK
     */
    public static final boolean ENABLE_WEIXIN_SDK = true;

    /**
     * 宏开关，是否打微博免登
     */
    public static final boolean ENABLE_WEIBO_SSO = true;

    /**
     * 宏开关，是否保留高德地图
     */
    public static final boolean ENABLE_GAODE_MAP = true;

    /**
     * 宏开关，是否打开安全扫描功能
     */
    public static final boolean ENABLE_SECURITY_SCAN = true;

    /**
     * 宏开关，是否启用磁力链下载支持
     */
    public static final boolean ENABLE_MAGNET_LINK_DOWNLOAD = false;


    /**
     * 是否使用国际版新左屏
     */
    public static final boolean ENABLE_INTERNATIONAL_NAVIGATION_HOMEPAGE = IS_INTERNATIONAL_VERSION;

    /**
     * 打开java log 的类型
     *  0: 关闭
     *  1: 打印到log cat
     *  2: 打印到文件
     *  3: 打印到Log cat 以及文件
     */
    public static  int ENABLE_JAVA_LOG_TYPE = 1;

    /**
     * 设置Log 的级别, 打印当前设置Log级别以及以上级别的Log
     *  0: verbose 级别
     *  1: debug 级别
     *  2: info 级别
     *  3: warning 级别
     *  4: error 级别
     */
    public static  int JAVA_LOG_LEVEL = 0;

    /**
     * 是否允许天气弹出提示
     */
    public static final boolean ENABLE_WEATHER_LOCATION = false;        
    
    /**
     * 创建桌面快捷方式,yz使用
     */
    public static final boolean ENABLE_YZ_CREATE_SHORTUT = false;
    
    /**
     * 预装的一键上网url地址
     */
    public static final String ONE_KEY_BROWSR_URL = "http://www.uc.cn";
        
    /**
     * Whether use the cmcc mm update sdk to check update file
     */
    public static final boolean ENABLE_CMCC_MM_UPDATE_SDK = false;
    
    /**
     * 是否验证预装渠道bid等信息的文件
     */
    public static final boolean ENABLE_VALIDATE_YZ_CHANNEL_FILE = false;

    /**
     * 是否允许后台消息推送（包括我们之前的推送和阿里的推送），默认是true，允许
     */
    public static final boolean ENABLE_BACKGROUND_PUSH_SERVICE = true;
    
    /**
     * 支持openwifi功能
     */
    public static final boolean ENABLE_OPEN_WIFI = false; 
       
    public static final boolean ENABLE_EXCEPTION_HANDLER_UI_TEST = false;
}