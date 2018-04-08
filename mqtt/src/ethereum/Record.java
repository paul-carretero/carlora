package ethereum;

import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>, or the
 * org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version 3.3.1.
 */
@SuppressWarnings("javadoc")
public class Record extends Contract {

    private static final String BINARY = "6060604052341561000f57600080fd5b60405160808061015083398101604052808051919060200180519190602001805191906020018051600095909555505060019190915560025560035560f7806100596000396000f300606060405260043610605c5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663188ec356811460615780636fea322d14608357806377a0d5d61460935780639b397e251460a3575b600080fd5b3415606b57600080fd5b607160b3565b60405190815260200160405180910390f35b3415608d57600080fd5b607160b9565b3415609d57600080fd5b607160bf565b341560ad57600080fd5b607160c5565b60005490565b60035490565b60025490565b600154905600a165627a7a72305820f21673caaccf00f170b4885cda74fbefbcc454f17640ff1fc07273e23921db4a0029";

    protected Record(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
	    BigInteger gasLimit) {
	super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Record(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
	    BigInteger gasLimit) {
	super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<BigInteger> getTimestamp() {
	final Function function = new Function("getTimestamp", Arrays.<Type>asList(),
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));
	return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<BigInteger> getSpd() {
	final Function function = new Function("getSpd", Arrays.<Type>asList(),
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));
	return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<BigInteger> getRot() {
	final Function function = new Function("getRot", Arrays.<Type>asList(),
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));
	return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @SuppressWarnings("rawtypes")
    public RemoteCall<BigInteger> getRpm() {
	final Function function = new Function("getRpm", Arrays.<Type>asList(),
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));
	return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /**
     * @param web3j
     * @param credentials
     * @param gasPrice
     * @param gasLimit
     * @param _timestamp
     * @param _rpm
     * @param _rot
     * @param _spd
     * @return RemoteCall<Record>
     */
    @SuppressWarnings("rawtypes")
    public static RemoteCall<Record> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
	    BigInteger gasLimit, BigInteger _timestamp, BigInteger _rpm, BigInteger _rot, BigInteger _spd) {
	String encodedConstructor = FunctionEncoder
		.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestamp),
			new org.web3j.abi.datatypes.generated.Uint256(_rpm),
			new org.web3j.abi.datatypes.generated.Uint256(_rot),
			new org.web3j.abi.datatypes.generated.Uint256(_spd)));
	return deployRemoteCall(Record.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @SuppressWarnings("rawtypes")
    public static RemoteCall<Record> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
	    BigInteger gasLimit, BigInteger _timestamp, BigInteger _rpm, BigInteger _rot, BigInteger _spd) {
	String encodedConstructor = FunctionEncoder
		.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestamp),
			new org.web3j.abi.datatypes.generated.Uint256(_rpm),
			new org.web3j.abi.datatypes.generated.Uint256(_rot),
			new org.web3j.abi.datatypes.generated.Uint256(_spd)));
	return deployRemoteCall(Record.class, web3j, transactionManager, gasPrice, gasLimit, BINARY,
		encodedConstructor);
    }

    public static Record load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
	    BigInteger gasLimit) {
	return new Record(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Record load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
	    BigInteger gasPrice, BigInteger gasLimit) {
	return new Record(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
