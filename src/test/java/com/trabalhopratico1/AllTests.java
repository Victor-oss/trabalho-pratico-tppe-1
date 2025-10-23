package com.trabalhopratico1;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CampeonatoTest.class, JogoTest.class, TimeTest.class })
@IncludeTags("funcional")
public class AllTests { }
