package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "account witness operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessVoteOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0c0764657a313333370a676f6f642d6b61726d6101";
    final String EXPECTED_TRANSACTION_HASH = "6d5dc43ba5d427e3aba3671ae06961c1090a952c3fc4397a61ea1feaf2b961b2";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dcebc80457010c0764657a313333370a676f6f642d6b61726d610100";

    private static AccountWitnessVoteOperation accountWitnessVoteOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");
        AccountName witness = new AccountName("good-karma");

        accountWitnessVoteOperation = new AccountWitnessVoteOperation(account, witness);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountWitnessVoteOperation);

        signedTransaction.setOperations(operations);
    }

    @Override
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(accountWitnessVoteOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
