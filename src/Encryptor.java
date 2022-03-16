public class Encryptor {
    /**
     * A two-dimensional array of single-character strings, instantiated in the constructor
     */
    private String[][] letterBlock;

    /**
     * The number of rows of letterBlock, set by the constructor
     */
    private int numRows;

    /**
     * The number of columns of letterBlock, set by the constructor
     */
    private int numCols;

    /**
     * Constructor
     */
    public Encryptor(int r, int c) {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock() {
        return letterBlock;
    }

    /**
     * Places a string into letterBlock in row-major order.
     *
     * @param str the string to be processed
     *            <p>
     *            Postcondition:
     *            if str.length() < numRows * numCols, "A" in each unfilled cell
     *            if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str) {
        /* to be implemented in part (a) */
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                int idxInStr = i * numCols + j;
                if(idxInStr < str.length()) {
                    letterBlock[i][j] = str.substring(idxInStr, idxInStr + 1);
                } else {
                    letterBlock[i][j] = "A";
                }
            }
        }
    }

    /**
     * Extracts encrypted string from letterBlock in column-major order.
     * <p>
     * Precondition: letterBlock has been filled
     *
     * @return the encrypted string from letterBlock
     */
    public String encryptBlock() {
        /* to be implemented in part (b) */
        String encrypted = "";
        for(int i = 0; i < numCols; i++) {
            for(int j = 0; j < numRows; j++) {
                encrypted += letterBlock[j][i];
            }
        }
        return encrypted;
    }

    /**
     * Encrypts a message.
     *
     * @param message the string to be encrypted
     * @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        /* to be implemented in part (c) */
        String encrypted = "";
        int maxIdx = numRows * numCols - 1;
        while(message.length() != 0) {
            fillBlock(message);
            encrypted += encryptBlock();
            if(maxIdx >= message.length()) {
                maxIdx = message.length() - 1;
            }
            message = message.substring(maxIdx + 1);
        }
        return encrypted;
    }

    /**
     * Decrypts an encrypted message. All filler 'A's that may have been
     * added during encryption will be removed, so this assumes that the
     * original message (BEFORE it was encrypted) did NOT end in a capital A!
     * <p>
     * NOTE! When you are decrypting an encrypted message,
     * be sure that you have initialized your Encryptor object
     * with the same row/column used to encrypted the message! (i.e.
     * the “encryption key” that is necessary for successful decryption)
     * This is outlined in the precondition below.
     * <p>
     * Precondition: the Encryptor object being used for decryption has been
     * initialized with the same number of rows and columns
     * as was used for the Encryptor object used for encryption.
     *
     * @param encryptedMessage the encrypted message to decrypt
     * @return the decrypted, original message (which had been encrypted)
     * <p>
     * TIP: You are encouraged to create other helper methods as you see fit
     * (e.g. a method to decrypt each section of the decrypted message,
     * similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage) {
        /* to be implemented in part (d) */
        String decrypted = "";
        int maxIdx = numCols * numRows - 1;
        while(encryptedMessage.length() > 0) {
            fillColumnMajor(encryptedMessage);
            decrypted += decryptBlock();
            if(maxIdx > encryptedMessage.length()) {
                maxIdx = encryptedMessage.length() - 1;
            }
            encryptedMessage = encryptedMessage.substring(maxIdx + 1);
        }
        boolean continueRemove = true;
        for(int i = decrypted.length() - 1; i >= 0; i--) {
            if(decrypted.substring(i, i + 1).equals("A") && continueRemove) {
                decrypted = decrypted.substring(0, i);
            } else {
                continueRemove = false;
            }
        }
        return decrypted;
    }

    public void fillColumnMajor(String str) {
        int index = 0;
        for(int i = 0; i < numCols; i++) {
            for(int j = 0; j < numRows; j++) {
                if(index > str.length() - 1) {
                    letterBlock[j][i] = "";
                } else {
                    letterBlock[j][i] = str.substring(index, index + 1);
                }
                index++;
            }
        }
    }

    public String decryptBlock() {
        String decrypt = "";
        for(String[] row : letterBlock) {
            for(String str : row) {
                decrypt += str;
            }
        }
        return decrypt;
    }

}