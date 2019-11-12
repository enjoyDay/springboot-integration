package com.springbootIntegration.demo.test.listener;

/**
 * @author liukun
 * @description
 * @date 2019/11/12
 */
public class Test {
    public static void main(String[] args) {
        EventSource source = new EventSource();
        source.setEventListener(event -> {
            //因为只有一个方法，是可以写成lambda表达式
            if (event.buttonClickEvent()==true){
                System.out.println("监听到有点击鼠标发生");
            } else if (event.mouseMoveEvent() == true){
                System.out.println("监听到有鼠标移动发生");
            }
        });
        // 传统旧方法
//        source.setEventListener(new EventListener() {
//            @Override
//            public void doHandle(Event event) {
//                if (event.clickButton()==true){
//                    System.out.println("监听到有点击鼠标发生");
//                } else if (event.moveMouse() == true){
//                    System.out.println("监听到有鼠标移动发生");
//                }
//            }
//        });

        source.buttonClick();
        source.mouseMove();
    }
}
