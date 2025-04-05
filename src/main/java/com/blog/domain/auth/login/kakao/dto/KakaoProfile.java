package com.blog.domain.auth.login.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {
	private long id;
	@JsonProperty("connected_at")
	private String connectedAt;
	private Properties properties;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	public long getId() {
		return id;
	}

	public String getConnectedAt() {
		return connectedAt;
	}

	public Properties getProperties() {
		return properties;
	}

	public KakaoAccount getKakaoAccount() {
		return kakaoAccount;
	}

	public static class Properties {
		private String nickname;
		@JsonProperty("profile_image")
		private String profileImage;

		@JsonProperty("thumbnail_image")
		private String thumbnailImage;

		public String getNickname() {
			return nickname;
		}
		public String getProfileImage() {
			return profileImage;
		}
		public String getThumbnailImage() {
			return thumbnailImage;
		}
	}

	public static class KakaoAccount {
		@JsonProperty("profile_nickname_needs_agreement")
		private Boolean profileNicknameNeedsAgreement;

		@JsonProperty("profile_image_needs_agreement")
		private Boolean profileImageNeedsAgreement;

		private Profile profile;

		@JsonProperty("has_email")
		private Boolean hasEmail;
		private String email;

		@JsonProperty("email_needs_agreement")
		private Boolean emailNeedsAgreement;

		@JsonProperty("is_email_valid")
		private Boolean isEmailValid;

		@JsonProperty("is_email_verified")
		private Boolean isEmailVerified;

		public Boolean getProfileNicknameNeedsAgreement() {
			return profileNicknameNeedsAgreement;
		}

		public Boolean getProfileImageNeedsAgreement(){
			return profileImageNeedsAgreement;
		}

		public String getEmail() {
			return email;
		}

		public Profile getProfile() {
			return profile;
		}

		public class Profile {
			private String nickname;
			@JsonProperty("thumbnail_image_url")
			private String thumbnailImageUrl;
			@JsonProperty("profile_image_url")
			private String profileImageUrl;
			@JsonProperty("is_default_image")
			private Boolean isDefaultImage;
			@JsonProperty("is_default_nickname")
			private Boolean isDefaultNickname;

			public String getNickname() {
				return nickname;
			}

			public Boolean getIsDefaultNickname() {
				return isDefaultNickname;
			}
		}
	}
}
