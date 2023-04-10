ArrayList()
 /**
    * 1、构造方法：
    *      ArrayList(): 初始化elementData,为期赋值默认的的空object数组
    *      ArrayList(size):校验size的合法性，size != 0,创建size大小的object数组，size == 0,赋值空object数组
    *      ArrayList(Collection):将添加集合转数组，将数组赋值给elementData，并给集合大小赋值，然后进行泛型擦出
    *
    * 2、添加元素add()
    *      a、先进判断是否需要扩容：
    *          1、判断是通过无参构造方法创建集合，第一次添加元素会默认扩容为10容量，后面继续添加会将添加元素
    *        后的最小容量与默认容量10取最大值，作为后面是否需要扩容的依据
    *          2、通过其他构造方法创建的直接走b逻辑
    *      b、得到添加元素后的最小容量使他与当前数组容量比较，大于的话，需要进行扩容
    *          扩容规则：将原数组容量扩大到1.5倍，如果还小于添加后的容量，则将添加后容量作为新扩容的容量。最大无法超过Int.maxValue
    *      c、处理完后，目标元素添加到扩容后的数组size位置，并将size++
    *
    * 3、添加元素add(index,element)
    *      a、先校验index合法性，是否越界
    *      b、判断是否需要扩容，逻辑同场景2
    *      c、将原数组中的index位置元素及后面全部元素往后挪一位
    *      d、将index位置的元素赋值为element，size ++
    *
    * 4、添加元素addAll(Collection)
    *      a、将添加集合转数组a
    *      b、校验是否需要扩容，逻辑同上
    *      c、将数组a拷贝到elementData数组上
    *      d、size += a.length
    *
    * 5、移除元素remove(index)
    *      a、校验index合法性，是否越界
    *      b、将index + 1 及后面所有的元素往前挪一位到index位置
    *      c、将size - 1位置的元素置空待GC回收
    *      d、size--
    *
    * 6、移除元素remove(element)
    *      a、判断element == null：
    *          1、for循环elementData数组，找到第一个元素为null的元素，并得到index；
    *          2、方法同场景5的步骤b、c、d
    *      b、element != null：
    *          1、for循环elementData数组，找到第一个元素值与目标元素值相等，并得到index；
    *          2、方法同上步骤2
    *
    *
    * 总结：ArrayList底层数据结构是数组，具有查找快，增删慢的特点，以及扩容也是一个个元素的挪动；ArrayList是有序的；避免频繁的增删操作
    */

LinkedList()
   /**
    * 1、构造方法：
    *      LinkedList():空实现
    *      LinkedList(Collection):内部走的是addAll的逻辑，下面会介绍
    *
    * 2、添加元素add(element)
    *      a、将element包装成Node并指向pre = last节点、next = null，将node节点链到last节点后
    *      b、判断last == null，即判断是否是空链表
    *          是：first指向node
    *          不是：将原最后一个节点last的next节点指向node，将node前一个节点pre指向原最后一个节点last
    *      c、最后将last节点指向node节点
    *
    * 3、添加元素add(index,element)
    *      a、先检查index合法性，是否越界
    *      b、通过二分法查找index，找到目标插入元素的位置,找到index位置节点xNode,构建element的node节点，其pre节点为xNode，next节点为xNode.next
    *      c、将node节点与前后节点链起来，xNode.pre.next节点为node节点，xNode.pre节点为node节点，
    *      d、size++
    *
    * 4、添加元素addAll(index,Collection)
    *      a、检查索引合法性，是否越界
    *      b、将插入的子集合转object数组a
    *      c、通过二分法找到index所在的插入的目标节点xNode
    *      c、for循环将数组a中的元素转Node节点，并将数组a转成链表，
    *      d、将xNode.pre.next节点指向插入子链表的第一个节点cFirstNode，将xNode.pre节点指向插入子链表的最后一个节点cLastNode
    *            如果插入的是队首，需要将first节点赋值cFirstNode节点
    *            如果插入的是队尾，需要将last节点赋值cLastNode节点
    *      e、size += c.size()
    *
    * 5、删除元素remove(element)
    *      a、先遍历容器，找到对应的node节点
    *      b、将node.pre.next指向node.next,将node.next.pre指向node.pre
    *      c、将node.pre = null;node.next = null; node.item = null
    *      d、size --
    *
    * 6、删除元素remove(index)
    *      a、先校验index合法性，是否越界
    *      b、通过二分法查找目标节点xNode
    *      c、操作同场景5
    *
    * 总结：LinkedList底层数据结构是链表，特点是增删快(插入队首/队尾,其他位置需要通过二分法找到相应位置的节点)、查找相比较慢
    */

SparseArray()
   /**
    * 1、构造方法
    *      SparseArray():无参构造方法，内部调用的是有参构造方法，并传入10作为默认大小
    *      SparseArray(Int):有参构造方法，判断传入的容量是否为0
    *          是：给mKeys初始化长度为0的int数组，给mValues初始化长度为0的Object数组
    *          否：给mValues创建一个指定长度的object数组，并给mKeys创建同长度的int数组
    *
    * 2、添加元素put(int,element)
    *      a、先通过二分法查找适合添加key所属的位置i：
    *          i >= 0:表示找到相同的key，直接覆盖mValues所对应的i索引值
    *          i < 0:返回的是key插入mKeys最合适的位置索引的补码
    *              1、对索引的补码进行取补，得到正确的索引值
    *              2、如果插入的位置小于集合大小并且插入的位置mValues恰巧是默认的Object对象(表示被remove的元素没有被内部gc置null)，
    *            这时候直接将对应的mKeys、mValues的值替换
    *              3、如果步骤2不满足，此时如果需要等待内部gc置null之前remove的元素，则执行内部gc方法(具体实现说明在下面remove方法中的gc内部实现)
    *              4、通过步骤3，会将有效的mKeys及mValues进行整理，将被remove的元素移除，并将有效值往前移动，保证数组的连续性
    *              5、然后对整理后的集合重新进行二分法查找key插入的合适位置i(因为此时数组的长度有变化，导致之前查找的位置无效)
    *              6、插入的位置找到，分别对mKeys、mValues进行检测是否需要扩容：将添加元素后的集合大小与对应的数组长度比较，如果大于则需要进行扩容到原来的2倍，反之不需要
    *                  a、扩容：新创建一个与原数组同类型且容量是之前的2倍的数组，将原先的数组从0 ～ i的位置元素拷贝到新数组中，再将i位置的元素改成目标
    *                值，最后将i ～ 剩的元素值拷贝到新数组中的i + 1的位置，并将新数组返回
    *                  b、不扩容：将i ～ 剩下有效的位置元素往后移一位，将目标元素放到i位置，返回原数组
    *              7、size ++
    *
    * 3、内部gc()
    *      a、执行for循环，循环次数是mKeys的大小
    *      b、循环体是对mValues的遍历，查询是否存在value = 默认的Object对象，存在就跳过此次循环
    *      c、不存在，就判断i 与 o 是否相等：相等则进行 o++，并执行下次循环
    *      d、不相等，就将mKeys、mValues对应i位置的元素赋值到o对应的位置，然后将mValues的i位置元素置null，等待系统gc清理
    *      e、结束循环后，将o赋值为size大小
    *
    * 4、添加元素putAll(SparseArray)
    *      将插入的列表进行遍历，然后获取对应的key、value再执行put方法
    *
    * 5、删除元素remove(key)
    *      a、通过二分法找到key所在的位置
    *      b、将mValues对应位置的元素设置为默认的Object对象(用于执行内部gc逻辑的判断)
    *      c、将需要执行gc的标识置为true(用于在操作集合数据时，是否需要做gc的判断)
    *
    *
    * 总结：SparseArray底层数据结构是数组，通过key所在的索引找到value，避免了hashMap内部的数组+链表+红黑树的重逻辑，因为使用数组，查找会较快
    * 避免频繁删除操作(频繁的删除可能会导致大量的Object对象无法被回收)
    */

HashMap()
/**
 * 1、构造方法：
 *      HashMap()：内部设置默认扩容系数，0.75
 *      HashMap(int)：内部调用两个参数构造方法，设置容量、设置默认扩容系数0.75
 *      HashMap(int,float)：内部先校验容量是否合法，再判断容量是否达到2^30，如果达到则设置容量为2^30，设置传入的扩容系数，计算扩容条件
 *      HashMap(Map)：
 *
 * 2、添加元素put()
 *      a、根据传入的key，计算出具有离散属性的hash值(将key进行hash ^ hash >> 16)
 *      b、添加元素：
 *          1、第一次添加元素(数组为null)：需要创建数组
 *              a、通过无参构造方法创建对象：创建一个大小为16的数组，计算出扩容参数(size * 0.75)
 *              b、通过有参构造方法创建对象：按传入的容量大小进行创建数组，计算扩容参数(同上)
 *           -> 得到新数组后，通过hash & (size - 1) 得到key所在新数组中的索引，然后将Node保存到对应位置
 *          2、存在数组：
 *              a、是树节点，将节点按红黑树的规则，添加到树中
 *              b、是链表节点，遍历链表，是否存在相同key，存在，则将对应节点替换，不存在就添加到链表最后一个节点上，判断链表的长度是否超过6，
 *            超过再判断数组长度>64，此时需要将链表转为树，否则对数组进行扩容
 *
 * 3、扩容resize()
 *      a、创建一个2倍容量的新数组
 *      b、循环遍历老数组
 *          1、当前节点不是链表、树：通过计算hash & (newSize - 1)得到在新数组中的位置，并设置元素
 *          2、当前节点是树：先将树拆成2个链表，并判断链表长度是否超过6，对超长的链表转为树后，都插入到数组中
 *          3、当前节点是链表：将链表拆分成2个链表(二分法拆成高低链)，然后分别插入到数组中(低链的位置为原先在老数组中的位置j，高链的位置为 j + oldSize)
 *      c、返回新数组
 *
 * 总结：
 *      1、非通过传入map的构造方法创建的对象，均只是创建hashMap对象，无内部数组初始化(在put元素时，才对数组进行初始化)
 *      2、hashMap的默认长度为16、链表的长度 <= 6
 *      3、扩容规则：链表长度 <= 6 && 数组长度 < 64 才会对数据进行扩容(扩到原来2倍)，否则 链表长度 > 6,会将链表转为红黑树
 *      4、hashMap是无顺序的：
 *          a、数组扩容：
 *              1、在数组扩容时，会对树表进行拆分为2个链表，如果链表长度过长，重新对超长链表转成红黑树，插入的数组中
 *              2、在数组扩容时，会对链表拆分成2个子链，重新插入数组中
 *          b、在添加元素时，如果插入到红黑树时，按红黑树的规则，需要保证树平衡，会导致树的旋转，导致祖、父、兄节点位置的变化
 *      5、hashMap的容量最大为2^30
 */