package mbaas.com.nifcloud.androidsegmentuserapp;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

public class Utils {
    /**
     * Perform action of waiting for request API.
     * @param millis The timeout of until when to wait for.
     */
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    public static void createUserIfNotExist(String userName, String pass) throws NCMBException {
        NCMBUser.loginInBackground(userName, pass, (ncmbUser, e) -> {
            if (ncmbUser == null) {
                NCMBUser user = new NCMBUser();
                user.setUserName(userName);
                user.setPassword(pass);
                user.saveInBackground(e1 -> {
                    if (e1 != null) {
                        Log.d("NCMB", e1.getMessage());
                    }
                });
            }
        });
    }

    public static void deleteUserIfNotExist(String userName, String pass) throws NCMBException {

        NCMBUser.loginInBackground(userName, pass, (ncmbUser, e) -> {
            if (e == null) {
                ncmbUser.deleteObjectInBackground(e1 -> {
                    if (e1 != null) {
                        Log.d("NCMB", e1.getMessage());
                    }
                });
            }
        });

    }
}
