/**
 * 
 */
package com.test.star.parse;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import brut.androlib.Androlib;
import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import brut.androlib.res.data.ResTable;
import brut.common.BrutException;
import brut.directory.DirectoryException;
import brut.directory.ExtFile;
import brut.util.OS;

/**
 * @author ocean-book
 *
 */
public class ApkToolClient {
	
    public final static short DECODE_RESOURCES_NONE = 0x0100;
    public final static short DECODE_RESOURCES_FULL = 0x0101;
    
    
    public static void tryToDecodeAll() {
		try {
			File inFile = new File("D:\\test\\pro999.apk");
			ApkDecoder decoder = new ApkDecoder();
			decoder.setOutDir(new File("D:\\test\\pro999_out"));
			decoder.setApkFile(inFile);
			decoder.setAnalysisMode(true, true);
			decoder.setDecodeResources(DECODE_RESOURCES_FULL);
			
			decoder.decode();
				
		} catch (AndrolibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void tryToDecodeJustManifestFile(String inApkFileName,String outPutDir) {
		ExtFile mApkFile = null;
		File inFile = null;
		File outPutAndroidManifestFile = null;
		try {
			inFile = new File(inApkFileName);
			File outDir = new File(outPutDir);
	        try {
	            OS.rmdir(outDir);
	        } catch (BrutException ex) {
	            throw new AndrolibException(ex);
	        }
	        outDir.mkdirs();
	        
	        mApkFile = new ExtFile(inFile);
			Androlib androlib = new Androlib();
			
			ResTable resTable =  getResTable(mApkFile,androlib);
			androlib.decodeManifestFull(mApkFile, outDir,resTable);
			System.out.println("after decode ,the pacakgeRenamed= "+resTable.getPackageRenamed());
			System.out.println("after decode ,the versionCode= "+resTable.getVersionInfo().versionCode);
			System.out.println("after decode ,the versionName= "+resTable.getVersionInfo().versionName);
            
			
			outPutAndroidManifestFile = new File(outPutDir+"\\"+"AndroidManifest.xml");

			if(outPutAndroidManifestFile.exists()) {
				outPutAndroidManifestFile.delete();
            }

		} catch (AndrolibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
	        if (mApkFile != null) {
	            try {
	                mApkFile.close();
	            } catch (IOException ignored) {}
	        }
		}
    }
    
    
    private static boolean hasResources(ExtFile mApkFile) throws AndrolibException {
        try {
            return mApkFile.getDirectory().containsFile("resources.arsc");
        } catch (DirectoryException ex) {
            throw new AndrolibException(ex);
        }
    }
    private static boolean hasManifest(ExtFile mApkFile) throws AndrolibException {
        try {
            return mApkFile.getDirectory().containsFile("AndroidManifest.xml");
        } catch (DirectoryException ex) {
            throw new AndrolibException(ex);
        }
    }
    
    private static ResTable getResTable(ExtFile mApkFile,Androlib androlib) throws AndrolibException {
    	ResTable mResTable = null;
            boolean hasResources = hasResources(mApkFile);
            boolean hasManifest = hasManifest(mApkFile);
            if (! (hasManifest || hasResources)) {
                throw new AndrolibException(
                        "Apk doesn't contain either AndroidManifest.xml file or resources.arsc file");
            }
            mResTable = androlib.getResTable(mApkFile, hasResources);
        return mResTable;
    }
    
    


	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
//		tryToDecodeAll();
		tryToDecodeJustManifestFile("D:\\\\test\\\\pro999.apk","D:\\test\\pro999_manifest_out");
	}
	
	
	

}