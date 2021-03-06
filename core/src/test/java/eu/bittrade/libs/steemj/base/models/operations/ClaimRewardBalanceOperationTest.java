package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "claim rewards balance operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "2706737465656d6a020000000000000003535445454d00000100000000000"
            + "000035342440000000003000000000000000656455354530000";
    final String EXPECTED_TRANSACTION_HASH = "6534a28421dda4ad5790f955ba72e30bee35c5838bc842bca07038fbe6fb0b09";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce7c80457012706737465656d6a020000000000000003535445454d00000100000000000000"
            + "03534244000000000300000000000000065645535453000000";

    private static ClaimRewardBalanceOperation claimRewardBalanceOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("steemj");

        Asset sbdReward = new Asset();
        sbdReward.setAmount(1L);
        sbdReward.setSymbol(AssetSymbolType.SBD);

        Asset steemReward = new Asset();
        steemReward.setAmount(2L);
        steemReward.setSymbol(AssetSymbolType.STEEM);

        Asset vestsReward = new Asset();
        vestsReward.setAmount(3L);
        vestsReward.setSymbol(AssetSymbolType.VESTS);

        claimRewardBalanceOperation = new ClaimRewardBalanceOperation(account, steemReward, sbdReward, vestsReward);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(claimRewardBalanceOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
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
