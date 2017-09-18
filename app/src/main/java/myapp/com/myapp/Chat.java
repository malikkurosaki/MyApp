package myapp.com.myapp;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by malik on 9/16/2017.
 */

public class Chat {
    int gambarUser;
    String namaUser,pesanUser;

    Chat(){
    }

    public Chat(int gambarUser, String namaUser, String pesanUser) {
        this.gambarUser = gambarUser;
        this.namaUser = namaUser;
        this.pesanUser = pesanUser;
    }

    public int getGambarUser() {
        return gambarUser;
    }

    public void setGambarUser(int gambarUser) {
        this.gambarUser = gambarUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getPesanUser() {
        return pesanUser;
    }

    public void setPesanUser(String pesanUser) {
        this.pesanUser = pesanUser;
    }
}
