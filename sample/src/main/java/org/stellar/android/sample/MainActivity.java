package org.stellar.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.HttpResponseException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Stellar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {

                // Generate ok https://www.stellar.org/laboratory/#account-creator?network=test
                String publicKey = "GAZQJ4VPNWBUMAD7MZM3XPO5QUJEEKIJZMXKXNUIX7NBLZVTR5SCUKGI";
                String secretKey = "SAO6SPWEBUHXKMDOPZ4JZTETXC4H4ZTLJYWXQCGR6WYN2DCDR26LTK3I";
                KeyPair pair = KeyPair.fromSecretSeed(secretKey);

                Server server = new Server("https://horizon-testnet.stellar.org");

                AccountResponse account = null;
                try {
                    account = server.accounts().account(pair);
                    Log.i(TAG, "Balances for account " + pair.getAccountId());
                    for (AccountResponse.Balance balance : account.getBalances()) {
                        Log.i(TAG, String.format(
                                "Type: %s, Code: %s, Balance: %s",
                                balance.getAssetType(),
                                balance.getAssetCode(),
                                balance.getBalance()));
                    }
                } catch (HttpResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
