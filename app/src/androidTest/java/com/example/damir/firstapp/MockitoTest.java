package com.example.damir.firstapp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;
/**
 * Created by Damir and Leora on 9/11/2017.
 */



public class MockitoTest {

    String UserName;
    String NonValidUserName;
    String Password;
    String PasswordWrong;
    String NonValidPassword;
    MainActivity Act;

    @Before
    public void initalize() {
        Act = mock(MainActivity.class);
        UserName = "Leora";
        NonValidUserName = "Dami";
        Password = "123456";
        PasswordWrong = "654321";
        NonValidPassword = "12345";
    }

    @Test
    public void newUserLength() {
        when(Act.registerUser(UserName, Password, Password)).thenReturn(true);
        assertTrue(Act.registerUser(UserName, Password, Password));
    }

    @Test
    public void newPassLength() {
        when(Act.registerUser(UserName, NonValidPassword, NonValidPassword)).thenReturn(true);
        System.out.println("ANSWER = "+Act.registerUser(UserName, NonValidPassword, NonValidPassword));
        assertTrue(Act.registerUser(UserName, NonValidPassword, NonValidPassword));
    }

//    @Test
//    public void newPassLength() {
//
//    }

}
