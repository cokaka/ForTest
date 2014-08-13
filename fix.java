//请不要使用package，这将会导致您的代码不能通过测试
import java.lang.*;
public class Fix{
    int Remain[25];
    int Incoming[25];
    int Outgoing[25];
    int arrayLen;

    public static void main(String[] args){
        
        
    }
    //判断当前行是否打印有问题
    public static boolean ifStrHavePro(String perStr){
        return perStr.indexOf('?')>0?true:false;
    }
    //找出当前行出现问题的位置
    public static int getLocation(String haveProblemStr){
        int l = 0;
        
        return l;      
    }
    //求和函数
    public static int sumOfArray(int inputArray[],int startIndex,int endIndex){
        int i = startIndex;
        int sumOfComput = 0;
        for(;i <= endIndex ;i++){
            sumOfComput += inputArray[i];
        }
        return sumOfComput;
    }
    //计算余额算法,向前递归或者向后递归，保证返回不为零，若因参数不足导致，则保持原值
    public static int computeRemain(int sierieNo){
        if(sierieNo == 0){
            return computePre(sierieNo);
        }else if(sierieNo == arrayLen-1){
            return computeAft(sierieNo);
        }else{
            
        }
    }
    //余额算法：向前递归
    public static int computePre(int sierieNo){
        return Remain[0]+sumOfArray(Incoming,1,sierieNo)-sumOfArray(Outgoing,1,sierieNo);
    }
    //余额算法：向后递归
    public static int computeAft(int sierieNo,int endIndex){
        return Remain[sierieNo+endIndex]-sumOfArray(Incoming,sierieNo+1,sierieNo+endIndex)+sumOfArray(Outgoing,sierieNo+1,sierieNo+endIndex);
    }
    //计算收入项
    public static int computeIncoming(int sierieNo){
        return computeRemain(sierieNo)-computeRemain(sierieNo-1)+Outgoing[sierieNo];
    }
    //计算支出项
    public static int computeOutgoing(int sierieNo){
        return Incoming[sierieNo]-(computeRemain(sierieNo)-computeRemain(sierieNo-1));
    }
    
    
    
    
    
}
