package com.snapdeal.ims.otp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.otp.dao.OTPInfoDao;
import com.snapdeal.ims.otp.dao.mapper.OTPInfoDaoMapper;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.internal.request.IsOTPVerifiedRequest;
import com.snapdeal.ims.otp.internal.request.UpdateInvalidAttemptsRequest;
import com.snapdeal.ims.otp.internal.request.UpdateOTPStatusRequest;
import com.snapdeal.ims.otp.internal.request.UpdateResendAttemptsRequest;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OtpEncryptionUtility;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;

/**
 * 
 * @author Abhishek
 *
 */
@Repository("OTPInfoDaoImpl")
public class OTPInfoDaoImpl implements OTPInfoDao {

	@Autowired
	@Qualifier("myBatisIMSDB")
	private SqlSessionTemplate sqlSession;

	@Override
	@Transactional
	public void saveOTP(UserOTPEntity otpDaoInfo) {
		// tested
		boolean isOTPEncryptionEnabled = Boolean
				.valueOf(Configuration
						.getGlobalProperty(ConfigurationConstants.OTP_ENCRYPTION_ENABLED));
		String otp=otpDaoInfo.getOtp();

		if(isOTPEncryptionEnabled) {
			otpDaoInfo.setOtp(OtpEncryptionUtility.encryptOTP(otp));
			sqlSession.insert(OTPInfoDaoMapper.SAVE_OTP_WITH_ENCRYPTION.toString(), otpDaoInfo);
		} else {
			sqlSession.insert(OTPInfoDaoMapper.SAVE_OTP.toString(), otpDaoInfo);
		}
		otpDaoInfo.setOtp(otp);
	}

	@Override
	@Transactional
	public Optional<UserOTPEntity> getOTPFromId(
			FetchLatestOTPRequest latestOtpInfo) {
		// tested
		List<UserOTPEntity> otpDaos = new ArrayList<UserOTPEntity>();

		otpDaos = sqlSession.selectList(
				OTPInfoDaoMapper.GET_OTP_FROM_ID.toString(), latestOtpInfo);
		if (otpDaos == null || otpDaos.isEmpty()) {
			return Optional.<UserOTPEntity> absent();
		}else{
			UserOTPEntity otp = otpDaos.get(0) ;
			if( !otp.getClientId().equalsIgnoreCase(latestOtpInfo.getClientId())){
				return Optional.<UserOTPEntity> absent();
			}else if(otp.getOtpStatus() != OTPStatus.ACTIVE){
				return Optional.<UserOTPEntity> absent();
			}else{
				return Optional.<UserOTPEntity> of(otpDaos.get(0));
			}
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void incrementInvalidAttempts(
			UpdateInvalidAttemptsRequest invalidAttemptsInfo) {
		// tested
		this.sqlSession.update(
				OTPInfoDaoMapper.INCREMENT_INVALID_ATTEMPTS.toString(),
				invalidAttemptsInfo);
	}

	@Transactional
	@Override
	public void incrementResendAttempts(
			UpdateResendAttemptsRequest resendAttemptsInfo) {
		// tested
		this.sqlSession.update(
				OTPInfoDaoMapper.INCREMENT_RESEND_ATTEMPTS.toString(),
				resendAttemptsInfo);

	}

	@Transactional
	@Override
	public void updateCurrentOTPStatus(UpdateOTPStatusRequest otpStatusInfo) {
		// tested
		this.sqlSession.update(
				OTPInfoDaoMapper.UPDATE_CURRENT_OTP_STATUS.toString(),
				otpStatusInfo);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Optional<UserOTPEntity> getOtpId(UserOTPEntity otpInfo) {
		// tested
		List<UserOTPEntity> otpDaos = new ArrayList<UserOTPEntity>();

		otpDaos = sqlSession.selectList(OTPInfoDaoMapper.GET_OTP_ID.toString(),
				otpInfo);

		if (otpDaos == null || otpDaos.isEmpty()) {
			return Optional.<UserOTPEntity> absent();
		}else{
			UserOTPEntity otp = otpDaos.get(0) ;
			if( otp.getOtp().length() == 4){
				if(!otp.getOtp().equals(otpInfo.getOtp())){
					return Optional.<UserOTPEntity> absent();
				}
			}else if(otp.getOtp().length() > 4){
				if(!otp.getOtp().equals(OtpEncryptionUtility.decryptOTP(otpInfo.getOtp()))){
					return Optional.<UserOTPEntity> absent();
				}
			}else if(StringUtils.isNotBlank(otpInfo.getToken())){
				if(StringUtils.isNotBlank(otp.getToken())){
					if(!otp.getToken().equals(otpInfo.getToken())){
						return Optional.<UserOTPEntity> absent();
					}else{
						return Optional.<UserOTPEntity> of(otpDaos.get(0));
					}
				}else{
					return Optional.<UserOTPEntity> absent();
				}
			}else if(StringUtils.isNotBlank(otp.getToken())){
				return Optional.<UserOTPEntity> absent();
			}else{
				return Optional.<UserOTPEntity> of(otpDaos.get(0));
			}
		}
		return Optional.<UserOTPEntity> of(otpDaos.get(0));
	}

	@Transactional
	@Override
	public Optional<UserOTPEntity> getLatestOTP(GenerateOTPServiceRequest request) {
		// tested
		List<UserOTPEntity> otpDaos = new ArrayList<UserOTPEntity>();

		otpDaos = sqlSession.selectList(
				OTPInfoDaoMapper.GET_LATEST_OTP.toString(), request);

		if(otpDaos == null || otpDaos.isEmpty()) {
			return Optional.<UserOTPEntity> absent();
		}else{
			UserOTPEntity otp = otpDaos.get(0) ;
			if(otp.getOtpStatus() != OTPStatus.ACTIVE){
				return Optional.<UserOTPEntity> absent();
			}else if(StringUtils.isNotBlank(request.getToken())){
				if(StringUtils.isNotBlank(otp.getToken())){
					if(!otp.getToken().equals(request.getToken())){
						return Optional.<UserOTPEntity> absent();
					}else{
						return Optional.<UserOTPEntity> of(otpDaos.get(0));
					}
				}else{
					return Optional.<UserOTPEntity> absent();
				}
			}else if(StringUtils.isNotBlank(otp.getToken())){
				return Optional.<UserOTPEntity> absent();
			}else{
				return Optional.<UserOTPEntity> of(otpDaos.get(0));
			}
		}
	}

	@Override
	public Optional<UserOTPEntity> isOTPVerified(IsOTPVerifiedRequest request) {
		List<UserOTPEntity> OTPDaos = new ArrayList<UserOTPEntity>();

		OTPDaos = sqlSession.selectList(
				OTPInfoDaoMapper.GET_VERIFIED_OTP.toString(), request);
		if (OTPDaos == null || OTPDaos.isEmpty()) {
			return Optional.<UserOTPEntity> absent();
		}else{
			UserOTPEntity otp = OTPDaos.get(0) ;
			if(otp.getClientId().equalsIgnoreCase(request.getClientId())){
				return Optional.<UserOTPEntity> of(OTPDaos.get(0));
			}else{
				return Optional.<UserOTPEntity> absent();
			}
		}
	}

}
