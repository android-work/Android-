
setContentView(R.layout.activity_main)
/**
    * activity中调用setContentView，先在AppCompatDelegateImpI.setContentView执行
    *      1、创建根布局，根据设置window窗口属性选择根布局root(一般root布局是LinearLayout->title->content)
    *      2、执行window.setContentView(root)
    *      3、获取DecorView，并将root添加到DecorView中(其中会执行viewRootImpI.requestFitSystemWindows,用于检查线程安全以及使用handler消息刷新页面)
    *      4、获取到root中到content布局，将我们绘制的xml解析出view并添加到content中
    *
    *
    * LayoutInflater加载布局
    *      1、先获取xml解析器
    *      2、通过解析器读取xml布局文件
    *      3、先读取第一个根节点，并获取节点名称，根据名称进行反射创建控件对象(判断name是否包含'.',如果包含，可以作为自定义控件，直接反射全路径名；如果不包含，则视为系统控件，在路径上添加'android.view.'，然后在进行反射控件对象)
    *      4、后面节点通过递归+while循环来读取(目的是将同一深度的控件添加到父布局中，然后递归一层层将子父布局添加到上层父布局中，都是以父布局的布局参数添加)
    *      5、结束xml布局解析后，返回layout，如果inflate传入的root!=null 或 attachParent == true;则会将layout以父布局参数添加到root中；否则直接返回layout
    */
        



startActivity()
 /**
     * startActivity()
     * 1、调用startActivity，通过一系列调用链，最终调用Activity.startForResultActivity()
     * 2、在startForResultActivity中，会调用Instrumentation.execStartActivity()，并携带一个IApplicationThread的IBind对象
     * 3、在execStartActivity中，获取到IActivityTaskManager的ActivityTaskManagerService代理对象(供应用进程调用服务进程)，并执行
     *   ActivityTaskManagerService.startActivity()，并将IApplicationThread的IBind对象传入(为服务进程调用应用进程)
     * 4、在startActivity中，最终会执行到ActivityStarter中的execute()，在这里面主要进行开启activity的请求，解析信息并将一系列信息封装到ActivityRecord中
     * 5、然后创建一个ClientTransaction对象，保存了应用进程IApplicationThread的IBind对象(用于服务进程与应用进程的通信)，以及IApplicationToken也是IBind对象，内部保存了ActivityRecord对象
     * 6、最后执行ClientTransaction.schedule()，内部调用IApplicationThread.scheduleTransaction(ClientTransaction)，夸进程通信
     * 7、在ApplicationThread.scheduleTransaction()中，会执行ActivityThread.scheduleTransaction()
     * 8、在ActivityThread中会执行父类的scheduleTransaction()，最终发送一个EXECUTE_TRANSACTION消息到ActivityThread.H的Handler内部类中
     * 9、在EXECUTE_TRANSACTION消息处理中，最后是根据Lifecycle的状态处理activity的生命周期
     * 10、ON_CREATE状态：
     *      a、执行ActivityThread.handleLaunchActivity()
     *      b、然后通过执行Instrumentation.newActivity()，内部通过反射创建一个activity对象
     *      c、然后从判断是否application对象，没有就执行Instrumentation.newApplication()，同样是通过反射创建Application
     *      d、application对象创建完毕后，指向application.attach()，在attach中，会将application的context对象保存到ContextWapper中
     *      e、attact执行完后，通过Instrumentation对象最终执行application.onCreate()
     *      f、application创建完毕后，接着执行activity的attach()，在attach中创建PhoneWindow，并保存activity的context到ContextWapper中
     *      g、之后在执行onCreate方法
     * 11、同理等Lifecycle的状态变化，执行对应状态的方法，最终回调activity的生命周期
     */
        
