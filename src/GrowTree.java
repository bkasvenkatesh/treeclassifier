/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Venkatesh
 */
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.matrix.Matrix;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.classifiers.RandomizableClassifier;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
 import java.util.Random;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class GrowTree extends RandomizableClassifier
{
    double distribution[];
    
     public static void main(String[] args)throws Exception
    {
        runClassifier(new GrowTree(),args);
        DataSource source = new DataSource("F:\\backup\\BTH\\#6DV2542 Machine Learning\\WEKA experiments\\UCI\\iris.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);
    }
     public void buildClassifier(Instances data) throws Exception 
     {
         growTree(data);
     }
    
    public boolean homogeneous(Instances D)
    {
        distribution=new double[D.numClasses()];
        Enumeration eninst=D.enumerateInstances();
        while(eninst.hasMoreElements())
        {
            Instance ele=(Instance)eninst.nextElement();
            distribution[(int) ele.classValue()]++;
        }
        
        int cnt=0;
        for(int i=0;i<D.numClasses();i++)
        {
            if(distribution[i]>0)
                cnt++;
        }
        if(cnt<=1)               // if all instances are of single class
         return true;
        else
         return false;
    }
    
     double label(Instances D)
    {
         Enumeration eninst=D.enumerateInstances();
       
            Instance ele=(Instance)eninst.nextElement();
            return ele.classValue();
    }
    
    Attribute bestSplit(Instances D)
    {
        double imin=1.0; Attribute fbest=null;
        Enumeration enat=D.enumerateAttributes();
       while(enat.hasMoreElements())
        {
            Attribute a=(Attribute)enat.nextElement();
            //split D into subsets d1 to dn based on values vi based on features
            Instances[] split = new Instances[a.numValues()];
            for (int i = 0; i < a.numValues(); i++) 
            {
                split[i] = new Instances(D, D.numInstances());
            }
            Enumeration x= D.enumerateInstances();
            while (x.hasMoreElements()) 
            {
                Instance in = (Instance) x.nextElement();
                split[(int) in.value(a)].add(in);
            }
            for (int i = 0; i < split.length; i++) 
            {
                split[i].compactify();
            }
            for(int i=0;i<a.numValues();i++)
            {
                if(imp(split[i])<imin)
                {
                    imin=imp(split[i]);
                    fbest=a;                    //evaluate the best feature to make root
                }
            }
        }
        return fbest;
                
    }
    
    Tree growTree(Instances D)
    {
        if(homogeneous(D))
            return label(D);
        
        Attribute S=bestSplit(D);
        
        //split D into D1...Dn
        Instances[] split = new Instances[S.numValues()];
        for (int i = 0; i < S.numValues(); i++) 
        {
            split[i] = new Instances(D, D.numInstances());
        }
        Enumeration x= D.enumerateInstances();
        while (x.hasMoreElements()) 
        {
            Instance in = (Instance) x.nextElement();
            split[(int) in.value(S)].add(in);
        }
        for (int i = 0; i < split.length; i++) 
        {
            split[i].compactify();
        }
        for(int i=0;i<S.numValues();i++)
        {
            if(split[i]!=null)
                Ti=growTree(split[i]);
            else
                return label(split[i]);
        }
        return tree whose root at S and children Di;
        
    }
   public void crossvalidaion()throws Exception
   {
       DataSource source = new DataSource("F:\\backup\\BTH\\#6DV2542 Machine Learning\\WEKA experiments\\UCI\\iris.arff");
       Instances newData = source.getDataSet();
       Evaluation eval = new Evaluation(newData);
       eval.crossValidateModel(tree, newData, 10, new Random(1));
       System.out.println(eval.toSummaryString("\nResults\n======\n", false));
   }
   
   public double imp(Instances data)
   {
       double localdistribution[]=new double[data.numClasses()];
       Enumeration eninst=data.enumerateInstances();
       while(eninst.hasMoreElements())
       {
            Instance ele=(Instance)eninst.nextElement();
            localdistribution[(int) ele.classValue()]++;
       }
        
        return imp;
   }
  
 
}
