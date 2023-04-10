 private val handler = Handler(Looper.myLooper()!!)
/**
     * Handler构造方法：
     *      1、无参、一参callback/async 的构造方法，最终都是调用 二参callback，async；内部处理是获取当前线程looper，并校验是否存在、从looper中获取消息队列
     *      2、带有loop的构造方法，最后都是调用 三个参数 loop，callback，async构造方法，内部逻辑就是赋值操作
     *   a、handler.sendMessage(msg)：通过发送消息
     *      1、内部调用链是执行sendMessageDelay(msg,time),只不过time = 0
     *      2、会将handle对象赋值给msg.target，用于后面取出message发送到handler中处理作准备
     *      3、然后会从looper中获取MessageQueue，并执行mQueue.enqueueMessage()，将msg存入队列中
     *
     *   b、handler.dispatchMessage(msg)：处理消息
     *      1、先判断msg.callback是否存在，存在则优先执行msg.callback.run()
     *      2、否则判断handler.callback是否被实现，存在则执行callback.handleMessage(msg)
     *      3、否则再执行handler.handleMessage(msg)，即我们自己实现的handler逻辑
     *
     *
     * Looper：
     *      1、通过Looper.prepare()静态方法创建Looper对象
     *      2、在prepare方法中，先从ThreadLocal中取出当前线程中的Looper，并校验是否存在Looper，如果存在则抛异常，不存在则new Looper()
     *      3、在Looper构造方法中，会去创建MessageQueue对象
     *   a、Looper.loop():开启循环遍历消息
     *      1、在looper的操作中，优先检查当前线程是否存在looper对象
     *      2、创建无限for循环，迭代消息队列中的msg，在取出msg时，MessageQueue.next()，内部会开启无限for循环，判断拿到的msg等待时常是否结束，是则返回，不是继续for循环
     *      3、如果当取出的msg不存在，则停止循环
     *      4、否则就执行msg.target.dispatchMessage(msg)到handler中
     *
     * ThreadLocal：
     *   a、set(value)：存入
     *      1、先获取到当前线程
     *      2、从当前线程中获取到ThreadLocalMap
     *      3、根据当前的ThreadLocal对象作为key，将value存入到ThreadLocalMap内部的健值对中
     *
     *   b、get()：取出
     *      1、先获取到当前线程
     *      2、从当前线程中获取到ThreadLocalMap
     *      3、根据当前的ThreadLocal对象作为key，从ThreadLocalMap内部中取出对应的value
     *
     *
     * Message:
     *      1、创建Message通过Message.obtain()静态方法创建，目的是为了复用之前的Message对象
     *      2、复用之前的Message，其逻辑就是将已处理过的Message中的状态全重置，然后将新的Message状态赋值给复用的Message中，达到Message对象可重复使用，无需创建过多Message对象
     *   a、enqueueMessage()：存消息
     *      1、在enqueueMessage中，按等待时间进行排列到消息队列中
     *          a、待执行消息：sMessage，目标消息：msg，临时消息变量：p，
     *          b、先取出待执行的消息sMessage
     *              1、如果sMessage不存在/msg.when == 0/msg.when < sMessage.when，都是将目标msg消息置为队列首位，并将sMessage指向msg
     *              2、否则，遍历队列，找到msg.when < p.when，并将msg插入到p的前面
     *
     * 总结：
     *      1、由于在Handler构造方法中回去校验当前线程是否存在Looper对象，所有我们在自线程中new Handler没有创建Looper会抛异常
     *      2、通过创建Looper对象，我们发现在线程中仅存在且唯一的Looper对象
     *      3、handler机制中的延迟消息，不是延迟发送，而是延迟从消息队列中取出，进而延迟处理
     *      4、ThreadLocal的存储机制是线程安全的，因为ThreadLocal是作为key存放在当前线程中的ThreadLocalMap的一个弱引用的一个key-value健值对中，而线程是独立的，其他线程无法访问当前线程
     *
     */