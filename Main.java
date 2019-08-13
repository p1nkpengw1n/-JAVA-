//Cr34t0r: @p1nkpengw1n
//This project was created to meet some guidelines which don't necessarily fit my conceptions. Note that no security is present in this stage.
//13.08.19//

package blockchain;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Blockchain implements Serializable {
    private static final long serialVersionUID = 7L;

    private volatile List<String> transactions;
    private volatile List<Block> blocks;

    private int idIncrementer = 1;
    public static int numberOfZeroes = 0;
    public static String numberOfZeroesString;

    public Blockchain() {
        blocks = Collections.synchronizedList(new ArrayList<Block>());
        transactions = Collections.synchronizedList(new ArrayList<String>());
    }

    public boolean validate(Block b, long elapsedTime) {
        if(blocks.size() > 0) {
            Block currentBlock = b;
            Block previousBlock = blocks.get(blocks.size()-1);
            if(currentBlock.getPrevHashCode().equals(previousBlock.getHashCode())
                    && currentBlock.getHashCode().substring(0,numberOfZeroes).equals(Blockchain.numberOfZeroesString)) {
                synchronized(b) {
                    blocks.add(b);
                    ++idIncrementer;
                    if(elapsedTime > 60000) {
                        numberOfZeroes--;
                        Blockchain.numberOfZeroesString = Blockchain.createZeroesString(numberOfZeroes);
                    }
                    else if(elapsedTime < 10000) {
                        numberOfZeroes++;
                        Blockchain.numberOfZeroesString = Blockchain.createZeroesString(numberOfZeroes);
                    }
                    return true;
                }
            }
            return false;
        }
        synchronized(b) {
            blocks.add(b);
            ++idIncrementer;
            if(elapsedTime > 60000) {
                numberOfZeroes--;
                Blockchain.numberOfZeroesString = Blockchain.createZeroesString(numberOfZeroes);
            }
            else if(elapsedTime < 10000) {
                numberOfZeroes++;
                Blockchain.numberOfZeroesString = Blockchain.createZeroesString(numberOfZeroes);
            }
            return true;
        }
    }

    public int getIdIncrementer() {
        return idIncrementer;
    }

    public int getNumberOfZeroes() {
        return numberOfZeroes;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void serialize() {
        try {
            FileOutputStream file = new FileOutputStream("Blockchain.ser");
            ObjectOutputStream outstr = new ObjectOutputStream (file);
            outstr.writeObject(this);
            outstr.close();
            file.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Blockchain deserialize(String fileName) {
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream instr = new ObjectInputStream (file);
            Blockchain bc = (Blockchain)instr.readObject();
            instr.close();
            file.close();
            return bc;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String createZeroesString(int numberOfZeroes) {
        int copyOfZeroes = numberOfZeroes;
        StringBuilder sb = new StringBuilder();
        while(copyOfZeroes > 0) {
            sb.append('0');
            copyOfZeroes--;
        }
        return sb.toString();
    }

    public void show() {
        for(Block b: this.getBlocks()) {
            System.out.println();
            System.out.println("Block:");
            System.out.println("Created by miner" + b.getMiner());
            System.out.println("miner" + b.getMiner() + " gets 100 VC");
            System.out.print("Id: ");
            System.out.println(b.getId());
            System.out.print("Timestamp: ");
            System.out.println(b.getTimeStamp());
            System.out.print("Magic number: ");
            System.out.println(b.getMagicNumber());
            System.out.println("Hash of the previous block:");
            System.out.println(b.getPrevHashCode());
            System.out.println("Hash of the block:");
            System.out.println(b.getHashCode());
            if(b.getPrevHashCode().equals("0")) {
                System.out.println("Block data: ");
                System.out.println("No transactions");
            }
            else {
                System.out.println("Block data: ");
                for(int i=0; i<b.getReplies().size(); i++) {
                    System.out.println(b.getReplies().get(i));
                }
            }
            System.out.println("Block was generating for " + b.getElapsedTime()/1000 + "." + b.getElapsedTime() % 1000 + " seconds");
            int numOfZeroes = b.getNumberOfZeroes()+1;
            System.out.println("N was increased to " + numOfZeroes);
        }
    }
}

class Block implements Serializable {
    private static final long serialVersionUID = 7L;

    private volatile List<String> replies;

    private int id;
    private long timeStamp;
    private String hashCode;
    private String prevHashCode;
    private long elapsedTime;
    private int magicNumber;
    private String miner;
    private int numberOfZeroes;

    public Block(int id, String prevHashCode, String currentThreadName, int numberOfZeroes) {
        this.id = id;
        this.prevHashCode = prevHashCode;
        this.hashCode = StringUtil.applySha256(this.toString());
        this.timeStamp = new Date().getTime();
        Random rand = new Random();
        this.magicNumber = 10000000 + rand.nextInt(90000000);
        this.miner = currentThreadName;
        this.numberOfZeroes = numberOfZeroes;
        this.replies = Collections.synchronizedList(new ArrayList<String>());
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getHashCode() {
        return hashCode;
    }

    public String getPrevHashCode() {
        return prevHashCode;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public String getMiner() {
        return miner;
    }

    public int getNumberOfZeroes() {
        return numberOfZeroes;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void show(String currentThreadName, int numberOfZeroes) {
        System.out.println();
        System.out.println("Block:");
        System.out.println("Created by miner # " + currentThreadName.substring(7,8));
        System.out.print("Id: ");
        System.out.println(this.getId());
        System.out.print("Timestamp: ");
        System.out.println(this.getTimeStamp());
        System.out.print("Magic number: ");
        System.out.println(this.getMagicNumber());
        System.out.println("Hash of the previous block:");
        System.out.println(this.getPrevHashCode());
        System.out.println("Hash of the block:");
        System.out.println(this.getHashCode());
        System.out.println("Block was generating for " + this.getElapsedTime()/1000 + "." + this.getElapsedTime() % 1000 + " seconds");
        if(this.getElapsedTime() < 10000) {
            System.out.println("N was increased to " + numberOfZeroes);
        }
        else if(this.getElapsedTime() > 60000) {
            System.out.println("N was decreased by 1");
        }
        else System.out.println("N stays the same");
    }

    @Override
    public String toString() {
        return super.toString() + this.magicNumber;
    }
}

class Miner extends Thread implements Runnable {

    public Blockchain bc;
    private ReentrantLock threadLock;
    private long elapsedTime;
    private String[] chats = {" just mined a block!"," found an appropriate new block!"," searched for and found a new block!",
            " might have found a compatible block!"," mined a legit block!"};
    private int VC = 0;

    public Miner(Blockchain bc, ReentrantLock threadLock) {
        this.bc = bc;
        this.threadLock = threadLock;
    }

    private Block mineBlock() {
        if(true == bc.getBlocks().isEmpty()) {
            long startTime = new Date().getTime();
            Block b = new Block(bc.getIdIncrementer(),"0",currentThread().getName().substring(7,8),Blockchain.numberOfZeroes);
            long endTime = new Date().getTime();
            elapsedTime = endTime - startTime;
            b.setElapsedTime(elapsedTime);
            return b;
        }
        else {
            long startTime = new Date().getTime();
            Block b = new Block(bc.getIdIncrementer(),bc.getBlocks().get(bc.getIdIncrementer()-2).getHashCode(),currentThread().getName().substring(7,8),Blockchain.numberOfZeroes);
            while(false == b.getHashCode().substring(0,Blockchain.numberOfZeroes).equals(Blockchain.numberOfZeroesString)) {
                b = new Block(bc.getIdIncrementer(), bc.getBlocks().get(bc.getIdIncrementer() - 2).getHashCode(), currentThread().getName().substring(7, 8), Blockchain.numberOfZeroes);
            }
            long endTime = new Date().getTime();
            //StringBuilder message = new StringBuilder(currentThread().getName());
            //message.append(chats[new Random().nextInt(5)]);
//            synchronized (bc) {
//                bc.getBlockReplies().add(message.toString());
//            }
            elapsedTime = endTime - startTime;
            b.setElapsedTime(elapsedTime);
            return b;
        }
    }

    @Override
    public void run() {
        while(true) {
            Block b = mineBlock();
            if(threadLock.tryLock()) {
                try{
                    if(true == bc.validate(b, b.getElapsedTime())) {
                        //b.show(currentThread().getName(),bc.getNumberOfZeroes());
                        this.VC += 100;
                        int sum = new Random().nextInt(this.VC);
                        StringBuilder message = new StringBuilder(currentThread().getName() + " sent " + sum +
                                " VC to another miner");
                        bc.getTransactions().add(message.toString());
                        this.VC -= sum;
                        for(String s: bc.getTransactions()) {
                            b.getReplies().add(s);
                        }
                        bc.getTransactions().clear();
                    }
                    else {
                        System.out.println("Block mined by this thread \"" + currentThread().getName() + "\" is invalid!");
                    }
                    break;
                }finally {
                    threadLock.unlock();
                }
            }
//            else try{
//                Thread.sleep(15); //Could work fine with lower digits, but it's (nearly) unnecessary performance-wise and it would introduce risks for normal execution-flow
//            }catch(InterruptedException ie){
//                throw new RuntimeException(ie);
//            }
        }
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public int getVC() {
        return VC;
    }
}

//DEMO-RUN

public class Main {
    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();
        ReentrantLock threadLock = new ReentrantLock();

        Miner miner1 = new Miner(blockchain,threadLock);
        Miner miner2 = new Miner(blockchain,threadLock);
        Miner miner3 = new Miner(blockchain,threadLock);
        Miner miner4 = new Miner(blockchain,threadLock);
        Miner miner5 = new Miner(blockchain,threadLock);
        Miner miner6 = new Miner(blockchain,threadLock);
//        Miner miner7 = new Miner(blockchain,threadLock);
//        Miner miner8 = new Miner(blockchain,threadLock);
//        Miner miner9 = new Miner(blockchain,threadLock);
//        Miner miner10 = new Miner(blockchain,threadLock);

        new Thread(miner1).start();
        new Thread(miner2).start();
        new Thread(miner3).start();
        new Thread(miner4).start();
        new Thread(miner5).start();
        new Thread(miner6).start();
//        new Thread(miner7).start();
//        new Thread(miner8).start();
//        new Thread(miner9).start();
//        new Thread(miner10).start();
         try{
             Thread.sleep(1000);
         }catch(InterruptedException ie) {

         }
         blockchain.show();
    }
}