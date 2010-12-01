package com.jeecms.core.util;

import java.io.File;

public interface FileOperator {
	/**
	 * д�ļ�
	 * 
	 * @param path
	 *            �ļ�·���������ļ�����
	 * @param content
	 *            ����
	 */
	public void writeFile(String path, String name, String content);

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param path
	 *            �ļ�·���������ļ�����
	 * @return
	 */
	public String readFile(String fileName);

	/**
	 * ɾ���ļ�
	 * 
	 * @param path
	 *            �ļ�·���������ļ�����
	 * @return �Ƿ�ɾ���ɹ�
	 */

	public boolean deleteFile(File file);

	public boolean copyFile(File src, File dist);

	public boolean copy(File src, File dist);

	public boolean writeFile(String url, File dist);
	
	public String fileName(String resUrl);
}
