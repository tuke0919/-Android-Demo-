package demo.jni.com.javeproject;


public class myClass {

    public static class ShareObject {

        private String name;
        private String pwd;

        String lock = new String();

        public void ObjectMethodA(){
            try {
                synchronized (this){
                    System.out.println("A begin time = " + System.currentTimeMillis());
                    Thread.sleep(1000);
                    System.out.println("A end time = " + System.currentTimeMillis());
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        public synchronized void ObjectMethodB(){

                System.out.println("B begin time = " + System.currentTimeMillis());
                System.out.println("B end   time = " + System.currentTimeMillis());
        }

        public void ObjectMethodC(){

            System.out.println("C begin time = " + System.currentTimeMillis());
            System.out.println("C end   time = " + System.currentTimeMillis());
        }

        public void ObjectMethodD(){
            try {
                synchronized (this){
                   for (int i = 1 ; i <= 5 ;i++){
                       System.out.println("synchronized thread name: " + Thread.currentThread().getName()+" -->i = "+i);
                       Thread.sleep(1000);
                   }
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


        public void setUserNamePassword (String userName ,String passWord){

            try {
                synchronized (lock){
                    System.out.println("thread name = " + Thread.currentThread().getName() + " 进入代码快: "+System.currentTimeMillis());
                    name = userName;
                    Thread.sleep(1000);
                    pwd = passWord;

                    System.out.println("thread name = " + Thread.currentThread().getName() + " 进入代码快: "+System.currentTimeMillis() + " 入参name： "+ name + " 入参pwd: "+ pwd);

                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

        public synchronized static void ObjectStaticMethodA(){
            try {

                System.out.println("static methodA begin 线程名称: " + Thread.currentThread().getName() + " times: " + System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println("static methodA end   线程名称: " + Thread.currentThread().getName() + " times: " + System.currentTimeMillis());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public synchronized static void ObjectStaticMethodB(){


            try {

                System.out.println("static methodB begin 线程名称: " + Thread.currentThread().getName() + " times: " + System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println("static methodB end   线程名称: " + Thread.currentThread().getName() + " times: " + System.currentTimeMillis());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    public static class ThreadA extends Thread {

        private ShareObject mShareObject;

        public ThreadA(String  name , ShareObject mShareObject){
            super(name);
            this.mShareObject = mShareObject;
        }

        @Override
        public void run() {
            super.run();
            if(mShareObject != null){
//                mShareObject.setUserNamePassword("a","aa");

                mShareObject.ObjectStaticMethodA();

            }
        }
    }


    public static class ThreadB extends Thread {

        private ShareObject mShareObject;

        public ThreadB(String  name , ShareObject mShareObject){
            super(name);
            this.mShareObject = mShareObject;
        }

        @Override
        public void run() {
            super.run();
            if(mShareObject != null){
//                mShareObject.setUserNamePassword("b","bb");
                mShareObject.ObjectMethodA();
//                mShareObject.ObjectMethodC();
            }
        }
    }



    public static void  main(String[] args){

        ShareObject objecta = new ShareObject();

        ThreadA a=new ThreadA("ThreadA",objecta);

        ShareObject objectb = new ShareObject();
        ThreadB b=new ThreadB("ThreadB",objectb);

        a.start();
        b.start();



    }


}
