package com.example.athena.Interfaces;

import androidx.fragment.app.Fragment;
/**
 * Interface for managing fragment transactions, including displaying and switching between fragments.
 */
public interface displayFragments {
    public void displayChildFragment(Fragment fragment);
    public void switchToNewFragment(Fragment fragment);
}
