package com.bsc.eRoots21testApp;

import com.google.gson.annotations.SerializedName;

public class ModelData {

    @SerializedName("LogisticRegression_category")
    public String LR_category;
    @SerializedName("LogisticRegression_accuracy")
    public String LR_accuracy;
    @SerializedName("LogisticRegression_probability_0")
    public String LR_prob_0;
    @SerializedName("LogisticRegression_probability_1")
    public String LR_prob_1;

    @SerializedName("DecisionTreeClassifier_category")
    public String DT_category;
    @SerializedName("DecisionTreeClassifier_accuracy")
    public String DT_accuracy;
    @SerializedName("DecisionTreeClassifier_probability_0")
    public String DT_prob_0;
    @SerializedName("DecisionTreeClassifier_probability_1")
    public String DT_prob_1;

    @SerializedName("RandomForestClassifier_category")
    public String RF_category;
    @SerializedName("RandomForestClassifier_accuracy")
    public String RF_accuracy;
    @SerializedName("RandomForestClassifier_probability_0")
    public String RF_prob_0;
    @SerializedName("RandomForestClassifier_probability_1")
    public String RF_prob_1;






}
