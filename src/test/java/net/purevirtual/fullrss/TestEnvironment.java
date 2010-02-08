package net.purevirtual.fullrss;

import com.google.apphosting.api.ApiProxy;

import java.util.HashMap;
import java.util.Map;

class TestEnvironment implements ApiProxy.Environment {

	@Override
	public String getAppId() {
		return "test";
	}

	@Override
	public String getVersionId() {
		return "1.0";
	}

	@Override
	public String getEmail() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLoggedIn() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAdmin() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAuthDomain() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestNamespace() {
		return "";
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("com.google.appengine.server_url_key", "http://localhost:8080");
		return map;
	}
}

