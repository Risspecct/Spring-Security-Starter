(function (window) {
  const LOGIN_PATH = "/index.html";

  function getAccessToken() {
    return localStorage.getItem("accessToken");
  }

  function getRefreshToken() {
    return localStorage.getItem("refreshToken");
  }

  function setTokens(accessToken, refreshToken) {
    if (accessToken) {
      localStorage.setItem("accessToken", accessToken);
    }
    if (refreshToken) {
      localStorage.setItem("refreshToken", refreshToken);
    }
  }

  function clearTokens() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  }

  async function parseJsonSafe(response) {
    const text = await response.text();
    if (!text) {
      return null;
    }
    try {
      return JSON.parse(text);
    } catch (_e) {
      return null;
    }
  }

  async function refreshAccessToken() {
    const refreshToken = getRefreshToken();
    if (!refreshToken) {
      return false;
    }

    const payload = JSON.stringify({ token: refreshToken });

    let response = await fetch("/api/auth/refresh-token", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: payload
    });

    if (response.status === 404) {
      response = await fetch("/refreshToken", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: payload
      });
    }

    if (!response.ok) {
      return false;
    }

    const data = await parseJsonSafe(response);
    if (!data || !data.accessToken) {
      return false;
    }

    setTokens(data.accessToken, data.token || refreshToken);
    return true;
  }

  async function apiRequest(path, options = {}, meta = {}) {
    const headers = {
      ...(options.headers || {})
    };

    if (options.body && !headers["Content-Type"]) {
      headers["Content-Type"] = "application/json";
    }

    if (!meta.skipAuth) {
      const token = getAccessToken();
      if (token) {
        headers.Authorization = `Bearer ${token}`;
      }
    }

    let response = await fetch(path, {
      ...options,
      headers
    });

    if (response.status === 401 && !meta.skipRefresh && !meta._retried) {
      const refreshed = await refreshAccessToken();
      if (refreshed) {
        return apiRequest(path, options, { ...meta, _retried: true });
      }
    }

    return response;
  }

  async function getCurrentUser() {
    const response = await apiRequest("/api/user/me", { method: "GET" });
    if (!response.ok) {
      return null;
    }
    return parseJsonSafe(response);
  }

  async function redirectByRole(userPath, adminPath) {
    const user = await getCurrentUser();
    if (!user) {
      clearTokens();
      window.location.replace(LOGIN_PATH);
      return;
    }

    const role = (user.role || "USER").toUpperCase();
    window.location.replace(role === "ADMIN" ? adminPath : userPath);
  }

  async function startOAuth(provider) {
    try {
      const response = await fetch(`/auth/login/${provider}`, { method: "GET" });
      if (response.ok) {
        const url = await response.text();
        if (url && url.startsWith("http")) {
          window.location.assign(url);
          return;
        }
      }
    } catch (_e) {
      // fallback below
    }

    window.location.assign(`/oauth2/authorization/${provider}`);
  }

  async function logout(options = {}) {
    try {
      const response = await apiRequest("/api/auth/logout", { method: "POST" }, { skipRefresh: true });
      if (!response.ok) {
        await apiRequest("/logout", { method: "POST" }, { skipRefresh: true });
      }
    } catch (_e) {
      try {
        await apiRequest("/logout", { method: "POST" }, { skipRefresh: true });
      } catch (_ignored) {
        // ignore network/logout errors
      }
    }

    clearTokens();

    if (options.redirect !== false) {
      window.location.replace(LOGIN_PATH);
    }
  }

  window.AuthClient = {
    LOGIN_PATH,
    getAccessToken,
    getRefreshToken,
    setTokens,
    clearTokens,
    apiRequest,
    getCurrentUser,
    refreshAccessToken,
    redirectByRole,
    startOAuth,
    logout
  };
})(window);
