package com.example.athena;

import com.example.athena.Models.User;
import com.example.athena.Models.DetailsForNotification;
import com.example.athena.Models.UserNotifDetails;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DetailsForNotificationUnitTest {
    @Test
    public void detailsForNotiCheckEquals(){
        UserNotifDetails user = new UserNotifDetails();
        user.setUserName("roger");
        DetailsForNotification mockModel = new DetailsForNotification(user,"wewe","213","invite");
        DetailsForNotification mockModel1 = new DetailsForNotification(user,"wewe","213","invite");
        DetailsForNotification mockModel2 = new DetailsForNotification(user,"wawa","313","pe");
        //check that it equals itself
        Assertions.assertTrue(mockModel.equals(mockModel));
        //Check that it does not equal another class or type
        Assertions.assertFalse(mockModel.equals(user));
        //check that it does equal another class with the same values
        Assertions.assertTrue(mockModel.equals(mockModel1));
        //check that it does not equal the same object type with different values
        Assertions.assertFalse(mockModel.equals(mockModel2));

    }

}
