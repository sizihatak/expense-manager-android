package com.project.sizihatak.expense_manager_android.unit;

import android.content.Context;

import com.project.sizihatak.expense_manager_android.view.CustomDialog;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

public class CustomDialogTest {

    CustomDialog dialog;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        dialog = new CustomDialog(context);
    }

    @Test
    public void testSeparateIntegerAndDecimal() throws Exception {
        StringBuilder integerString = new StringBuilder("");
        StringBuilder decimalString = new StringBuilder("");
        dialog.separateIntegerAndDecimal("1234 123,12 грн.", integerString, decimalString);
        assertEquals("1234123", integerString.toString());
        assertEquals("12", decimalString.toString());
    }

    @Test
    public void testDeleteSpaces() throws Exception {
        StringBuilder integerString = new StringBuilder("1 123 123 123 212");
        dialog.deleteSpaces(integerString);
        assertEquals("1123123123212", integerString.toString());
    }

    @Test
    public void testAddedSpaces() throws Exception {
        StringBuilder integerString = new StringBuilder("123123123");
        integerString = dialog.addSpaces(integerString);
        assertEquals("123 123 123", integerString.toString());

        integerString = new StringBuilder("12312312");
        integerString = dialog.addSpaces(integerString);
        assertEquals("12 312 312", integerString.toString());

        integerString = new StringBuilder("1231231");
        integerString = dialog.addSpaces(integerString);
        assertEquals("1 231 231", integerString.toString());

        integerString = new StringBuilder("123123");
        integerString = dialog.addSpaces(integerString);
        assertEquals("123 123", integerString.toString());

        integerString = new StringBuilder("12312");
        integerString = dialog.addSpaces(integerString);
        assertEquals("12 312", integerString.toString());

        integerString = new StringBuilder("1231");
        integerString = dialog.addSpaces(integerString);
        assertEquals("1 231", integerString.toString());

        integerString = new StringBuilder("123");
        integerString = dialog.addSpaces(integerString);
        assertEquals("123", integerString.toString());
    }

    @Test
    public void testSetIntegerCost() throws Exception {
        String currentCost = "12,30 грн.";
        String result = dialog.setCost("9", currentCost, true);
        assertEquals("129,30 грн.", result);

        currentCost = "0,30 грн.";
        result = dialog.setCost("9", currentCost, true);
        assertEquals("9,30 грн.", result);

        currentCost = "123 123,30 грн.";
        result = dialog.setCost("9", currentCost, true);
        assertEquals("1 231 239,30 грн.", result);

        currentCost = "123 123 123,30 грн.";
        result = dialog.setCost("9", currentCost, true);
        assertEquals("123 123 123,30 грн.", result);
    }

    @Test
    public void testSetDecimalCost() throws Exception {
        String currentCost = "0,30 грн.";
        String result = dialog.setCost("9", currentCost, false);
        assertEquals("0,90 грн.", result);

        currentCost = result;
        result = dialog.setCost("9", currentCost, false);
        assertEquals("0,99 грн.", result);

        currentCost = result;
        result = dialog.setCost("9", currentCost, false);
        assertEquals("0,99 грн.", result);

    }

    @Test
    public void testEraseIntegerCost() throws Exception {
        String currentCost = "23,30 грн.";
        String result = dialog.eraseCost(currentCost, true);
        assertEquals("2,30 грн.", result);

        currentCost = result;
        result = dialog.eraseCost(currentCost, true);
        assertEquals("0,30 грн.", result);

        currentCost = "0,30 грн.";
        result = dialog.eraseCost(currentCost, true);
        assertEquals("0,30 грн.", result);
    }

    @Test
    public void testEraseDecimalCost() throws Exception {
        String currentCost = dialog.setCost("9", "123,00 грн.", false);
        currentCost = dialog.setCost("9", currentCost, false);
        currentCost = dialog.setCost("9", currentCost, false);

        String result = dialog.eraseCost(currentCost, false);
        assertEquals("123,90 грн.", result);

        currentCost = result;
        result = dialog.eraseCost(currentCost, false);
        assertEquals("123,00 грн.", result);

        currentCost = result;
        result = dialog.eraseCost(currentCost, false);
        assertEquals("12,00 грн.", result);
    }
}