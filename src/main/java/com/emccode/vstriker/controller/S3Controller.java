package com.emccode.vstriker.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import vStrikerBizModel.ApiBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class S3Controller {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateS3;
	@FXML
	private Button saveS3;
	@FXML
	private TextField s3accesskey;
	@FXML
	private TextField s3url;
	@FXML
	private TextField s3secretkey;
	@FXML
	private TextField s3bucket;
	@FXML
	private ChoiceBox<String> chooseProtocol;
	@FXML
	private TextField s3port;

	private VStriker vStriker;
	private Account acct;
	private Api api;

	// Constructor
	public S3Controller() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker, Account validAcct) {
		this.vStriker = vStriker;
		this.acct = validAcct;
	}

	public void updateS3Api(VStriker vStriker, Account validAcct, Api api) {
		this.vStriker = vStriker;
		this.acct = validAcct;
		this.api = api;
		s3accesskey.setText(api.getSubtenant());
		s3url.setText(api.getUrl());
		s3secretkey.setText(api.getSecretKey());
		s3bucket.setText(api.getBucket());
		chooseProtocol.setValue(api.getUrl().contains("https") ? "https"
				: "http");
		s3port.setText(api.getUrl().contains("https") ? api
				.getHttpsAddressPort() : api.getHttpAddressPort());
		saveS3.setText("Update");
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In S3Controller initialize");
		chooseProtocol.setItems(FXCollections.observableArrayList("http",
				"https"));
		chooseProtocol.setValue("http");
		s3accesskey.setPromptText("Access key");
		s3url.setPromptText("URL to service");
		s3secretkey.setPromptText("Secret key");
		s3bucket.setPromptText("Bucket name");
		s3port.setPromptText("Port number");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateS3Clicked(ActionEvent event) {
		System.out.println("Validate S3 button clicked");
		/*
		 * Read these variables from the screen - ToDo String S3_ACCESS_KEY_ID =
		 * "user045"; String S3_SECRET_KEY =
		 * "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg"; String S3_ENDPOINT =
		 * "http://object.vipronline.com"; String S3_ViPR_NAMESPACE = null;
		 */
		String S3_ViPR_NAMESPACE = null;
		if (s3accesskey.getText() == null
				|| s3accesskey.getText().length() == 0
				|| s3url.getText() == null || s3url.getText().length() == 0
				|| s3secretkey.getText() == null
				|| s3secretkey.getText().length() == 0) {
			System.out.println("Please set Access Key, URL and Secret Key");
			return;
		}
		if (acct != null) {
			vStriker.showApiValidation((int) acct.getAccountId());
		} else {
			vStriker.postStatus("A valid account is missing");
		}
	}

	@FXML
	public void saveS3Clicked(ActionEvent event) throws Exception {
		System.out.println("Save S3 button clicked");
		if (s3accesskey.getText() == null
				|| s3accesskey.getText().length() == 0
				|| s3url.getText() == null || s3url.getText().length() == 0
				|| s3secretkey.getText() == null
				|| s3secretkey.getText().length() == 0
				|| s3bucket.getText() == null
				|| s3bucket.getText().length() == 0) {
			System.out
			.println("Please set Access Key, URL, Secret Key and Bucket name");
			return;
		}
		if (saveS3.getText().equals("Update")) {
			// Update the existing api object
			api.setSubtenant(s3accesskey.getText());
			api.setUrl(s3url.getText());
			api.setSecretKey(s3secretkey.getText());
			api.setBucket(s3bucket.getText());
			switch (chooseProtocol.getValue()) {
			case "https":
				api.setHttpsAddressPort(s3port.getText());
				break;
			case "http":
				api.setHttpAddressPort(s3port.getText());
				break;
			default:
				System.out.println("Api type needs to be http or https");
			}
			ApiBiz.ApiUpdate(api);
			vStriker.postStatus("API data saved!");
		} else {
			Api s3api = new Api();
			s3api.setAccount(acct);
			s3api.setSubtenant(s3accesskey.getText());
			s3api.setUrl(s3url.getText());
			s3api.setSecretKey(s3secretkey.getText());
			s3api.setBucket(s3bucket.getText());
			s3api.setHttpAddressIp("tobechanged");
			// This should change
			// setProtocol
			// s3api.setHttpAddressPort("999"); // This should change to setPort
			// s3api.setApiTypeId(2); // This depends on protocol - if protocol
			// is
			// http
			// or https

			List<vStrikerEntities.ApiType> apitypelist = vStrikerBizModel.ApiTypeBiz
					.ApiTypeSelectAll();

			switch (chooseProtocol.getValue().toString()) {
			case "http":
				for (vStrikerEntities.ApiType a : apitypelist) {
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http"))) {
						s3api.setApiType(a);
						break;
					}
				}
				if (s3port.getText() == null || s3port.getText().length() == 0) {
					s3port.setText("80");
					s3api.setHttpAddressPort("80");
				} else {
					s3api.setHttpAddressPort(s3port.getText());
				}
				if (!s3url.getText().toLowerCase().matches("^\\w+://.*")) {
					// if (!s3url.getText().toLowerCase().startsWith("http://")
					// ||
					// !s3url.getText().toLowerCase().startsWith("https://")) {
					s3url.setText("http://" + s3url.getText());
					s3api.setUrl(s3url.getText());
				}
				break;
			case "https":
				for (vStrikerEntities.ApiType a : apitypelist) {
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https"))) {
						s3api.setApiType(a);
						break;
					}
				}
				if (s3port.getText() == null || s3port.getText().length() == 0) {
					s3port.setText("443");
					s3api.setHttpAddressPort("443");
				} else {
					s3api.setHttpAddressPort(s3port.getText());
				}
				if (!s3url.getText().toLowerCase().matches("^\\w+://.*")) {
					// if (!s3url.getText().toLowerCase().startsWith("http://")
					// ||
					// !s3url.getText().toLowerCase().startsWith("https://")) {
					s3url.setText("https://" + s3url.getText());
					s3api.setUrl(s3url.getText());
				}
				break;
			default:
				System.out.println("Api type needs to be http or https");

			}
			// Add protocol and port after entity is updated - ToDo
			try {
				ApiBiz.ApiCreate(s3api);
				vStriker.postStatus("API data saved!");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to create S3 Api");
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void deleteS3Clicked(ActionEvent event) {
		System.out.println("Delete S3 button clicked");
		try {
			ApiBiz.ApiDelete(api.getApiId());
			s3accesskey.clear();
			s3url.clear();
			s3secretkey.clear();
			s3bucket.clear();
			s3port.clear();
			saveS3.setText("Save");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
