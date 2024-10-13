import {expect, Page, test} from '@playwright/test';
import {ICreateVenueRequest} from "../models/venue";
import {ICreateMealRequest} from "../models/ICreateMealRequest";
import {openAddDialog, populateAddMealDialog, populateAddVenueDialog} from "./utils";


test('has title', async ({ page }) => {
  await page.goto('/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle("FoodOrder");
});


test('add venue test', async ({ page }) => {
  await page.goto('/');

  await openAddDialog('venue', page)
  const createVenueRequest: ICreateVenueRequest = {
    name: "add venue test",
    description: "description",
    location: "London",
    menuItems: [
      {
        name: 'name',
        description: 'description',
        price: 0.5
      }
    ]
  }
  await populateAddVenueDialog(createVenueRequest, page)
  expect(page.getByText('Venue Added')).toBeTruthy()
});

test('test add meal', async ({ page }) => {
  await page.goto('/');
  const createVenueRequest: ICreateVenueRequest = {
    name: "add add meal",
    description: "description",
    location: "London",
    menuItems: [
      {
        name: 'name',
        description: 'description',
        price: 0.5
      }
    ]
  }

  const createMealRequest: ICreateMealRequest = {
    dateOfMeal: 0,
    location: "London",
    mealConfig: {
      draft: false,
      privateMealConfig: undefined
    },
    name: "name",
    venueName: "add add meal"
  }

  await openAddDialog('venue', page)
  await populateAddVenueDialog(createVenueRequest, page)

  await openAddDialog('meal', page)
  await populateAddMealDialog(createMealRequest, page)
  expect(page.getByText('Meal added')).toBeTruthy()
});
