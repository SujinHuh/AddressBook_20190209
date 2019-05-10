/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 *
 * @author JaeKi Lee
 */
public class AddressBook {
  private int capacity;
  private int length;
  ArrayList<Personal> personals;
  
  public int GetLength(){
      return this.length;
  }
  
  public int GetCapacity(){
      return this.capacity;
  }
  public AddressBook(){
      this.personals = new ArrayList<>(125);
      this.capacity = 125;
      this.length = 0;
  }
  
  public AddressBook(int capacity){
      this.personals = new ArrayList<>(capacity);
      this.capacity = capacity;
      this.length = 0;
  }
  
  public AddressBook(AddressBook source){
      this.length = source.length;
      this.capacity = source.capacity;
      this.personals = source.personals;
  }
  
  public int Record(String name, String addrress, String telephoneNumber, String emailAddrress) {
      int index = 0;
     
      Personal personal = new Personal(name, addrress, telephoneNumber, emailAddrress);
      this.personals.add(personal);
      
      if(this.capacity == this.length){
          this.capacity++;
      }
      
      this.length++;
      index = this.GetLength() - 1;
      return index;
  }
  
  public int Erase(int index){
      this.personals.remove(index);
      this.length--;
      this.capacity--;
      return -1;
  }
  
  public void Arrange() {
     //AscendingPersonal ascendingPersonal = new AscendingPersonal();
      //Collections.sort(personals, ascendingPersonal);
       //this.personals.sort((Comparator<? super addressbook.Personal>) personals);
       Collections.sort(personals,new AscendingPersonal());
  }
  
  public int Correct(int index, String address, String telephoneNumber, String emailAddress){
      
      Personal personal = this.personals.get(index);
      personal = new Personal(personal.getName(), address, telephoneNumber, emailAddress);
      this.personals.set(index, personal);
      return index;
  }
  
  public Personal GetAt(int index){
      return this.personals.get(index);
  }
  
  public int[] Find(String name) {
    int i=0;
    int j=0;
    int count=0;
    
    while(i<this.GetLength()){    
        if(this.personals.get(i).getName().compareTo(name)==0){
            count++;
        }
        i++;
    }
      int[] indexes=new int[count];
    i=0;
    while(i<this.GetLength()){
        if(this.personals.get(i).getName().compareTo(name)==0){
            indexes[j]=i;
            j++;
        }
        i++;
    }
      return indexes;
  }
  
  public int CompareNames(Personal one, Personal other) {
      return one.getName().compareTo(other.getName());
  }
  
 class AscendingPersonal implements Comparator<Personal> {
     
     @Override
     public int compare(Personal one, Personal other) {
         return one.getName().compareTo(other.getName());
     }
 }
}