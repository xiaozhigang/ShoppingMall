import com.shopmall.springIoc.BeanExample;
import com.shopmall.springIoc.BeanFactory;
import org.junit.Test;

/**
 * @author xiao
 * @data 2024/4/1 20:04
 */
public class IoCTest {
    @Test
    public void test_BeanFactory() {
        //1.创建bean工厂(同时完成了加载资源、创建注册单例bean注册器的操作)
        BeanFactory beanFactory = new BeanFactory();

        //2.第一次获取bean（通过反射创建bean，缓存bean）
        BeanExample beanExample1 = (BeanExample) beanFactory.getBean("beanExample");
        beanExample1.print("xxxx");

        //3.第二次获取bean（从缓存中获取bean）
        BeanExample beanExample2 = (BeanExample) beanFactory.getBean("beanExample");
        beanExample2.print("yyyy");
    }
}
