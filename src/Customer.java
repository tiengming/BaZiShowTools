public class Customer {
    private String name;
    private int genderSymbol;
    private String birthtime;

    public Customer(String name, int genderSymbol, String birthtime) {
        this.name = name;
        this.genderSymbol = genderSymbol;
        this.birthtime = birthtime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGenderSymbol() {
        return genderSymbol;
    }

    public void setGenderSymbol(int genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public String gender(int genderSymbol) {
        if (genderSymbol == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    public String getDetails() {
        return name + "\t" + gender(genderSymbol) + "\t" + birthtime;
    }
}
