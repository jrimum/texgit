package br.com.nordestefomento.jrimum.texgit.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com">Gilmar P.S.L.</a>
 * 
 * @since 
 * 
 * @version 
 */
	
public class FileUtil {

	public static final String ENTER = "\r\n";


	
	/**
	 * <p>
	 *  Método responsável pela leitura de qualqer arquivo. Cada linha do arquivo
	 * corresponde a um item da lista retornada. Caso tenha lido algum arquivo
	 * ou não houve poblema na leitura, retorna List senão null
	 * </p>
	 * 
	 * @param pathName
	 * @return sucess
	 * 
	 * @since 
	 */
		
	public static List<String> read(String pathName) {

		if (pathName != null) {

			try {

				return readFree(new File(pathName));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * <p>
	 * Método responável pela leitura de qualqer arquivo. Cada linha do arquivo
	 * corresponde a um item da lista retornada. Caso tenha lido algum arquivo
	 * ou não houve poblema na leitura, retorna List senão null.
	 * </p>
	 * 
	 * @param file
	 * @return
	 * 
	 * @since 
	 */
		
	public static List<String> read(File file) {

		if (file != null) {

			try {

				return readFree(file);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static List<String> readFree(File file) throws IOException {

		List<String> archive = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String s;

		do {
			s = reader.readLine();
			if (s != null) {
				archive.add(s);
			}
		} while (s != null);

		reader.close();

		return archive;
	}

	public static void createOthers(File file, String tagName, int lineBuffer)
			throws IOException {

		List<String> archive = new ArrayList<String>(lineBuffer);

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String s;

		int count = 0;
		int countFile = 1;
		
		String path = StringUtils.substringBefore(file.getAbsolutePath(), "\\"
				+ file.getName())
				+ "\\";

		do {

			s = reader.readLine();

			if (s != null) {
				archive.add(s + "\r\n");
				count++;
			}

			if ((count % lineBuffer == 0) && count > 1) { // multiplos

				createFile(path + tagName + countFile + ".txt", archive);
				archive = new ArrayList<String>(lineBuffer);
				countFile++;
			}

		} while (s != null);

		if (!archive.isEmpty()) {
			createFile(path + tagName + countFile + ".txt", archive);
		}

		reader.close();
	}

	
	
	/**
	 * <p>
	 * Método responsável pela marcação de qualqer arquivo.
	 * </p>
	 * 
	 * @param pathName
	 * @param tag
	 * @return sucess
	 * 
	 * @since 
	 */
		
	public static boolean mark(String pathName, String tag) {

		if (pathName != null & tag != null) {

			File arch = new File(pathName);
			File newArch = new File(pathName + tag);

			arch.renameTo(newArch);
			
			return true;
		}
		return false;
	}


	
	/**
	 * <p>
	 * Método responsável pela renomeação de qualqer arquivo
	 * </p>
	 * 
	 * @param pathDir
	 * @param name
	 * @param wishName
	 * @return sucess
	 * 
	 * @since 
	 */
		
	public static boolean renameAs(String pathDir, String name,
			String wishName) {

		if (pathDir != null & name != null & wishName != null) {

			File arch = new File(pathDir + "/" + name);
			File newArch = new File(pathDir + "/" + wishName);

			arch.renameTo(newArch);
			
			return true;
		}

		return false;
	}

	
	
	/**
	 * <p>
	 * Cria um arquivo a partir de uma única string com o layout da mesma. Ou
	 * seja, se a string tem quebra de linha o arquivo também terá.
	 * </p>
	 * 
	 * @param path
	 * @param content
	 * @return sucess
	 * 
	 * @since 
	 */
		
	public static boolean createFile(String path, String content) {

		try {

			File arch = null;

			arch = new File(path);

			BufferedWriter wtr = new BufferedWriter(new FileWriter(arch));
			wtr.write(content);
			wtr.flush();
			wtr.close();

			arch.setWritable(true);
			arch.setReadable(true);
			arch.setExecutable(true);

			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;

	}

	
	/**
	 * <p>
	 * Cria um arquivo com várias linhas a partir de uma lista de strings. Para
	 * cada item da lista uma linha será criada no arquivo.
	 * </p>
	 * 
	 * @param path
	 * @param content
	 * 
	 * @since 
	 */
		
	public static void createFile(String path, List<String> content) {

		File arq = null;

		arq = new File(path);

		write(arq, content);

	}

	
	
	/**
	 * <p>
	 *  Cria um arquivo com vária linhas a partir de uma lista de strings. Para
	 * cada item da lista uma linha será criada no arquivo.
	 * </p>
	 * 
	 * @param file
	 * @param content
	 * 
	 * @since 
	 */
		
	public static void write(File file, List<String> content) {

		try {

			BufferedWriter wtr = new BufferedWriter(new FileWriter(file));
			for (String c : content) {
				wtr.write(c);
			}

			wtr.flush();
			wtr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * <p>
	 * Cria um arquivo a partir de uma única string.
	 * </p>
	 * 
	 * @param file
	 * @param content
	 * 
	 * @since 
	 */
		
	public static void write(File file, String content) {

		try {

			BufferedWriter wtr = new BufferedWriter(new FileWriter(file));

			wtr.write(content);

			wtr.flush();
			wtr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
