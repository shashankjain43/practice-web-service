
package com.snapdeal.ums.services.facebook;

import java.util.Set;

import com.snapdeal.ums.core.entity.facebook.FacebookLike;

public interface IFacebookLikeServiceInternal {

    public Set<FacebookLike> getUserFacebookLikesBySDId(Long id);

    public Set<FacebookLike> getUserFacebookLikesByFBId(Long id);

    public Set<FacebookLike> getUserFacebookLikesByEmail(String email);

    public void addFaceBookLike(FacebookLike fbLike);

    public void updateFacebookLike(FacebookLike fbLike);

}
