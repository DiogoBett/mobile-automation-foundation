package io.github.diogobett.runner;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import io.github.diogobett.constants.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages(Constants.ANDROID_FEATURE_PACKAGE)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = Constants.GLUE_PACKAGE)
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = Constants.ANDROID_TAG)
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = Constants.ANDROID_CUCUMBER_PLUGINS)
public class AndroidRunner {}
