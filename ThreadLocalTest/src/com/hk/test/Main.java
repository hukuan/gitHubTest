package com.hk.test;

import java.util.Random;


public class Main {

	static ThreadLocal<StudentBean> studentThread;
	static StudentBean student = new StudentBean();

	static class UpdataStudentRunable implements Runnable {

		@Override
		public void run() {
			setStudentId(0);
		}

		private void setStudentId(int id) {
			// 获取当前线程的名字
			String currentThreadName = Thread.currentThread().getName();
			System.out.println(currentThreadName + " is running!");
			Random r = new Random();
			int i = r.nextInt(100);
			System.out.println("thread " + currentThreadName + " set id to:" + i);
			// 获取一个Student对象，并将随机数年龄插入到对象属性中
			getStudent();
			studentThread.get().setId(i);
			System.out.println("thread " + currentThreadName
					+ " first read id is:" + studentThread.get().getId());
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.out.println("thread " + currentThreadName
					+ " second read id is:" + studentThread.get().getId());
		}

	}
	
	private static StudentBean getStudent(){
		StudentBean stu =studentThread.get();
		if (null==stu) {
			stu = new StudentBean();
		}
		studentThread.set(stu);
		return stu;
	}
	public static void main(String[] args) {
		Main.UpdataStudentRunable runable = new Main.UpdataStudentRunable();
		studentThread = new ThreadLocal<>();
		Thread t1 = new Thread(runable, "a");
		Thread t2 = new Thread(runable, "b");
		t1.start();
		t2.start();
	}

}
