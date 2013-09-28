



import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;



/**
 * For more information about this algorithm, see
 * http://archives1.twoplustwo.com/showflat.php?Cat=0&Number=8513906&page=0&fpart=1&vc=1
 * @author Chris Oei http://www.linkedin.com/in/christopheroei
 */
public class TwoPlusTwo 

{

	
    /**
     * HandRanks.dat
     * http://static.eluctari.com/download/game/poker/HandRanks.dat<br/>
     * Size: 129951336<br/>
     * CRC32: 7808da57<br/>
     * MD5: 5de2fa6f53f4340d7d91ad605a6400fb<br/>
     * SHA1: f8467e36f470c9beea98c47d661c9b2c4a13e577<br/>
     * SHA256: ad00f3976ad278f2cfd8c47b008cf4dbdefac642d70755a9f20707f8bbeb3c7e<br/>
     */
    private static final String HAND_RANK_DATA_FILENAME = "C:\\Users\\Mungojerrie\\Desktop\\workspace\\HandRanks.dat";
    private static final int HAND_RANK_SIZE = 32487834;
    private static int HR[] = new int[HAND_RANK_SIZE];
    //private static Logger log = Logger.getLogger(TwoPlusTwo.class);
    public static String[] HAND_RANKS = {"BAD!!", "High Card", "Pair", "Two Pair", "Three of a Kind",
    	"Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};

    /**
     * Calculate the poker hand rank of seven cards.
     * @param cards		an integer array
     * @param offset	evaluate the next seven cards starting at this position
     * @return			the hand rank
     */
    public  int lookupHand7(int[] cards, int offset) {
        int pCards = offset;
        int p = HR[53 + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        return HR[p + cards[pCards]];
    }

    /**
     * Calculate the poker hand rank of five cards.
     * @param cards		an integer array
     * @return			the hand rank
     */
    public static int lookupHand5(int[] cards) {
        int pCards = 0;
        int p = HR[53 + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards]];
        p = HR[p];
        return HR[p];
    }
    
    /**
     * Converts a little-endian byte array to a Java (big-endian) integer.
     * We need this because the HandRanks.dat file was generated using
     * a little-endian C program and we want to maintain compatibility.
     * @param b
     * @param offset
     * @return
     */
    private static final int littleEndianByteArrayToInt(byte[] b, int offset) {
        return (b[offset + 3] << 24) + ((b[offset + 2] & 0xFF) << 16)
                + ((b[offset + 1] & 0xFF) << 8) + (b[offset] & 0xFF);
    }
    
    
 

    static {
        int tableSize = HAND_RANK_SIZE * 4;
        byte[] b = new byte[tableSize];
        InputStream br = null;
        try {
            br = new BufferedInputStream(new FileInputStream(HAND_RANK_DATA_FILENAME));
            int bytesRead = br.read(b, 0, tableSize);
         
        } catch (FileNotFoundException error) {
            System.out.println("Error:"+error.getMessage());
        } catch (IOException error) {
        	System.out.println("Error:"+error.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException error) {
            	System.out.println("Error:"+error.getMessage());
            }
        }
        for (int i = 0; i < HAND_RANK_SIZE; i++) {
            HR[i] = littleEndianByteArrayToInt(b, i * 4);
        }
        /* Card to integer conversions:
        2c =  1    2d =  2    2h =  3    2s =  4
        3c =  5    3d =  6    3h =  7    3s =  8
        4c =  9    4d = 10    4h = 11    4s = 12
        5c = 13    5d = 14    5h = 15    5s = 16
        6c = 17    6d = 18    6h = 19    6s = 20
        7c = 21    7d = 22    7h = 23    7s = 24
        8c = 25    8d = 26    8h = 27    8s = 28
        9c = 29    9d = 30    9h = 31    9s = 32
        Tc = 33    Td = 34    Th = 35    Ts = 36
        Jc = 37    Jd = 38    Jh = 39    Js = 40
        Qc = 41    Qd = 42    Qh = 43    Qs = 44
        Kc = 45    Kd = 46    Kh = 47    Ks = 48
        Ac = 49    Ad = 50    Ah = 51    As = 52
      */
      
        
    	
       
        
        
    }
    
    


   
    


}