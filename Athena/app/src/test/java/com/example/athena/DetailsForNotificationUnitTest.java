package com.example.athena;

import com.example.athena.Models.User;
import com.example.athena.Models.detailsForNotification;
import com.example.athena.Models.userNotifDetails;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DetailsForNotificationUnitTest {
    @Test
    public void detailsForNotiCheckEquals(){
        userNotifDetails user = new userNotifDetails();
        user.setUserName("roger");
        detailsForNotification mockModel = new detailsForNotification(user,"wewe","213","invite");
        detailsForNotification mockModel1 = new detailsForNotification(user,"wewe","213","invite");
        detailsForNotification mockModel2 = new detailsForNotification(user,"wawa","313","pe");
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
