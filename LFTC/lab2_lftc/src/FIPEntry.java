class FIPEntry {
    String token;
    int code;

    public FIPEntry(String token, int code) {
        this.token = token;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Token: (" + token + "), Cod: " + code;
    }
}
