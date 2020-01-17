package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CapturesScenariosSteps {
    private Environment environment;

    @When("^I add (\\d+) captures$")
    public void i_add_captures(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I get captures about a trainer at specific page (\\d+) with specific size (\\d+)$")
    public void i_get_captures_about_a_trainer_at_specific_page_with_specific_size(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the system returns the (\\d+) status and size (\\d+) with captures$")
    public void the_system_returns_the_status_and_size_with_captures(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a new capture$")
    public void a_new_capture() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I create a new capture$")
    public void i_create_a_new_capture() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the system returns the added capture with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_status(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I get information about this capture with the new pokemon$")
    public void i_get_information_about_this_capture_with_the_new_pokemon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the system returns the added capture with pokemon with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_pokemon_with_status(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I get information about this capture with the new trainer$")
    public void i_get_information_about_this_capture_with_the_new_trainer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the system returns the added capture with trainer with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_trainer_with_status(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I try to get the capture about the new pokemon and this new user$")
    public void i_try_to_get_the_capture_about_the_new_pokemon_and_this_new_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I try to get the capture about the new trainer and this new user$")
    public void i_try_to_get_the_capture_about_the_new_trainer_and_this_new_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I get information about captures with a unexisted pokemon$")
    public void i_get_information_about_captures_with_a_unexisted_pokemon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I get information about captures with a unexisted trainer$")
    public void i_get_information_about_captures_with_a_unexisted_trainer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
