<!DOCTYPE html>
<html>
<head>
    <title>Access Token</title>
</head>
<body>
<div class="accessToken">Access Token: ${accessToken.accessToken}</div>
<div class="userId">User ID: ${accessToken.userId}</div>
<div class="expires">Expires At: ${accessToken.expires}</div>
<script type="text/javascript">
    if (opener.handleAuthentication) {
        opener.handleAuthentication('${accessToken.accessToken}', '${accessToken.expires}');
        window.close();
    }
</script>
</body>
</html>