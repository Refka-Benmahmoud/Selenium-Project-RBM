package com.seleniumrbm.Context;

import com.seleniumrbm.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Data shared between the steps of ONE scenario (PicoContainer creates a new one per scenario). */
public class ScenarioData {
    public String name = "Student";
    public String email;
    public Map<String, String> account = TestData.defaultAccount();
    public List<String> addedProducts = new ArrayList<>();
}
