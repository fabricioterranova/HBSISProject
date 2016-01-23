package com.fabriciooliveira.hbsisproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

/**
 * Created by fabriciooliveira on 1/22/16.
 */
public class Pessoa implements Parcelable {

    @SerializedName("nome")
    private String nome;

    @SerializedName("sobrenome")
    private String sobrenome;

    @SerializedName("dataNascimento")
    private String dataNascimento;

    private boolean isAtivo;

    public Pessoa() {

    }

    public Pessoa(String nome, String sobrenome, String dataNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
    }

    public Pessoa(String nome, String sobrenome, String dataNascimento, boolean isAtivo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.isAtivo = isAtivo;
    }

    public Pessoa(Parcel input) {
        this.nome = input.readString();
        this.sobrenome = input.readString();
        this.dataNascimento = input.readString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeString(this.sobrenome);
        dest.writeString(this.dataNascimento);
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {

        public Pessoa createFromParcel(Parcel in) {

            return new Pessoa(in);
        }

        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
        }
    };
}
