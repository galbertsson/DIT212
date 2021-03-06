package com.example.ohimarc.marc.presenter;


import com.example.ohimarc.marc.service.UserStorageFactory;
import com.example.ohimarc.marc.view.startMenuView.StartMenuView;
import com.example.ohimarc.marc.model.MemorizationTrainingTool;
import com.example.ohimarc.marc.service.UserStorage;
import com.example.ohimarc.marc.view.startMenuView.UserViewHolder;

import java.util.List;

/**
 * @author Gustav Albertsson
 * <p>
 * This class is repsonsible for handling communication between the StartMenu and the model
 */
public class StartMenuPresenter {

    private List<String> users;
    private final UserStorage store;
    private final StartMenuView view;

    /**
     * Creates a StartMenuPresenter which retrieve its persistent state from the given path
     *
     * @param filePath The absolute filepath where the persistent state file is saved
     */
    public StartMenuPresenter(StartMenuView view, String filePath) {
        store = UserStorageFactory.createLocalUserStorage(filePath);
        this.view = view;

        //Set up the MemorizationTrainingTool with stored values
        MemorizationTrainingTool global = MemorizationTrainingTool.getInstance();
        MemorizationTrainingTool mtt = store.getStoredState();
        global.setActiveUser(mtt.getActiveUserId());
        global.setUsers(mtt.getUsers());

        users = global.getUserNames();
    }

    /**
     * Method for setting up one row of the recyclerView
     *
     * @param index   The position of the row in the recyclerView
     * @param rowView The UserViewHolder object which holds the information for that row
     */
    public void onBindUserRowViewAtPosition(UserViewHolder rowView, int index) {
        rowView.setUsername(users.get(index));
    }

    /**
     * Method for getting how many rows there should be in the start menu view
     *
     * @return The number of rows there should be in the start menu view
     */
    public int getUserRowsCount() {
        return users != null ? users.size() : 0;
    }

    /**
     * Method for handling clicking on an row in the recycler view, This method will login the user
     * at the given position
     *
     * @param adapterPosition The index of the row that was clicked
     */
    public void onUserClickedAtPosition(int adapterPosition) {
        MemorizationTrainingTool.getInstance().setActiveUser(adapterPosition);
        store.storeState(MemorizationTrainingTool.getInstance());
        view.login();
    }

    /**
     * Method for handling long clicking on an row in the recycler view, This method will ask the user
     * if they want to delete the user
     *
     * @param adapterPosition The index of the row that was clicked
     */
    public void onUserLongClickedAtPosition(int adapterPosition) {
        view.promptForDeletion(adapterPosition, users.get(adapterPosition));
    }

    /**
     * @return Returns true if a user is currently logged in, otherwise returns false
     */
    public boolean loggedIn() {
        return MemorizationTrainingTool.getInstance().getActiveUser() != null;
    }

    /**
     * Given a name of a user creates a new user and saves it persistently
     *
     * @param name The name of the user that is going to be added
     */
    public void createUser(String name) {
        MemorizationTrainingTool.getInstance().addNewUser(name);
        store.storeState(MemorizationTrainingTool.getInstance());
        users = MemorizationTrainingTool.getInstance().getUserNames();
    }

    /**
     * Method used to confirm the deletion of a user with a specific index, when this method is called with a valid
     * index the user will be removed, if the index is invalid no changes will be made
     *
     * @param index The index with the user has in the list of users
     */
    public void confirmDeletion(int index) {
        MemorizationTrainingTool.getInstance().removeUser(index);
        store.storeState(MemorizationTrainingTool.getInstance());
        users = MemorizationTrainingTool.getInstance().getUserNames();
    }
}
