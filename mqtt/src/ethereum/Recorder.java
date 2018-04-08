package ethereum;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 * Une instance de recorder permettant d'enregistrer des entrée utilisateur dans
 * la blockchain ethereum
 */
public class Recorder {

    /**
     * GAS_PRICE
     */
    private static BigInteger GAS_PRICE = BigInteger.valueOf(1000);

    /**
     * GAS_LIMIT
     */
    private static BigInteger GAS_LIMIT = BigInteger.valueOf(1000000);

    /**
     * Instance de Web3J
     */
    private final Web3j web3;

    /**
     * Credentials pour le compte courrant
     */
    private Credentials credentials;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * créé une instance du recorder qui servira à enregistrer
     */
    public Recorder() {
	this.web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/

	Web3ClientVersion web3ClientVersion;
	try {
	    web3ClientVersion = this.web3.web3ClientVersion().send();
	    String clientVersion = web3ClientVersion.getWeb3ClientVersion();
	    this.credentials = WalletUtils.loadCredentials("here_is_password", "/home/carretero/git/carlora/wallet");
	    System.out.println(clientVersion);
	} catch (IOException | CipherException e) {
	    System.err.println(e.getMessage());
	}
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    /**
     * Ajoute une entrée dans la blockchain ethereum en créant un smart constract
     * pour l'utilisateur défini
     * 
     * @param e
     * @return true si le smart contract a été validé
     */
    public boolean createRecord(final Entry e) {
	BigInteger ts = BigInteger.valueOf(System.currentTimeMillis());
	BigInteger rpm = e.getRpm();
	BigInteger spd = e.getSpd();
	BigInteger rot = e.getRot();

	boolean isValid = false;

	try {
	    Record contract = Record.deploy(this.web3, this.credentials, GAS_PRICE, GAS_LIMIT, ts, rpm, rot, spd)
		    .send();
	    isValid = contract.isValid();
	} catch (Exception err) {
	    System.err.println(err.getMessage());
	}
	System.out.println("RECORDED [" + e.toString() + "] => " + isValid);
	return isValid;
    }

}
