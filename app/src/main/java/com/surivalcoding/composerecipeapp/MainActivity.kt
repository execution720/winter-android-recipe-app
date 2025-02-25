package com.surivalcoding.composerecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.surivalcoding.composerecipeapp.presentation.component.SearchRecipeScreen
import com.surivalcoding.composerecipeapp.presentation.component.splash.SplashScreen
import com.surivalcoding.composerecipeapp.presentation.component.home.HomeScreen
import com.surivalcoding.composerecipeapp.presentation.component.navigation.Route
import com.surivalcoding.composerecipeapp.presentation.component.signIn.SignInScreen
import com.surivalcoding.composerecipeapp.presentation.component.signUp.SignUpScreen
import com.surivalcoding.composerecipeapp.presentation.component.viewModel.RecipeViewModel
import com.surivalcoding.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        val viewModel: RecipeViewModel by viewModels { RecipeViewModel.Factory }

        setContent {
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()

            ComposeRecipeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Route.SplashScreen) {
                        composable<Route.SplashScreen> {
                            SplashScreen(
                                modifier = Modifier
                                    .padding(),
                                onClick = {
                                    navController.navigate(Route.SignInScreen) {
                                        popUpTo(Route.SplashScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Route.SignInScreen> {
                            SignInScreen(
                                modifier = Modifier
                                    .padding(innerPadding),
                                onSignUpClick = {
                                    navController.navigate(Route.SignUpScreen) {
                                        popUpTo(Route.SignInScreen) { inclusive = true }
                                    }
                                },
                                onSignInButtonClick = {
                                    navController.navigate(Route.HomeScreen) {
                                        popUpTo(Route.SignInScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Route.SignUpScreen> {
                            SignUpScreen(
                                modifier = Modifier
                                    .padding(innerPadding),
                                onClick = {
                                    navController.navigate(Route.SignInScreen) {
                                        popUpTo(Route.SignUpScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Route.HomeScreen> {
                            HomeScreen(
                                modifier = Modifier
                                    .padding(innerPadding),
                                onClick = {
                                    navController.navigate(Route.SearchScreen) {
                                        popUpTo(Route.HomeScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Route.SearchScreen> {
                            scope.launch {
                                viewModel.injectRecipeDataToState()
                            }

                            SearchRecipeScreen(
                                modifier = Modifier
                                    .padding(innerPadding),
                                onValueChanged = viewModel::onValueChanged,
                                state = viewModel.state.collectAsState()
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
// SavedRecipesScreen 사용 예제
lifecycleScope.launch {
                        viewModel.injectRecipeDataToState()
                    }
                    SavedRecipesScreen(
                        modifier = Modifier
                            .padding(innerPadding),

                        state = viewModel.state.collectAsState()
                    )
                    /*SavedRecipesScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        foodImage = viewModel.state.value.foodImages,
                        titles = viewModel.state.value.titles,
                        chefNames = viewModel.state.value.chefNames,
                        rates = viewModel.state.value.rates,
                        cookingTimeMinutes = viewModel.state.value.cookingTimeMinutes,
                        dataCount = viewModel.state.value.dataCount,
                        state = viewModel.state.collectAsState()
                    )*/
                    /*SavedRecipesScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        foodImage = viewModel.getFoodImage(),
                        titles = viewModel.getRecipeTitles(),
                        chefNames = viewModel.getChefNames(),
                        rates = viewModel.getRatings(),
                        cookingTimeMinutes = viewModel.getCookingTimes(),
                        dataCount = viewModel.getDataCount(),
                        state = viewModel.state.collectAsState()
                    )*/
 */

/*
// SearchRecipeScreen 사용 예제
SearchRecipeScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        typing = typing,
                        onValueChanged = { newText ->
                            typing = newText
                        },
                        state = viewModel.state.collectAsState()
                    )
 */

/*
// SearchRecipeScreen 사용 예제
var typing by remember { mutableStateOf(TextFieldValue("")) }
SearchRecipeScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        typing = typing,
                        onValueChanged = { newText ->
                            if(newText.text == "") {
                                viewModel.endSearching()
                            } else {
                                viewModel.startSearching()
                            }
                            lifecycleScope.launch {
                                viewModel.onInputTextChanged(newText)
                            }
                            typing = newText
                        },
                        state = viewModel.state.collectAsState()
                    )
 */

/*
// 네비게이션 활용 사용 예제

val navController = rememberNavController()     이런 식의 선언은 다른 파일로 빼서 관리
            val navGraph = remember(navController) {        그래프를 만들어 전달해 줄 수도 있다. NavHost 함수를 잘 찾아서 사용할 것.
                navController.createGraph(startDestination = "SplashScreen") {
                    addGraph()
                }
            }

            lifecycleScope.launch {     코루틴 스코프를 뭣도 모르고 lifecycle을 이용해 만들었지만,
                    viewModel.injectRecipeDataToState()
                }
                val scope = rememberCoroutineScope()        이렇게 rememberCoroutineScope로 만들어 주자.

    NavHost(navController = navController, startDestination = Route.SplashScreen) {     // 이 부분 때문에 꺾쇠를 못 사용한 것
                        composable<Route.SplashScreen> {
                            SplashScreen(
                                modifier = Modifier
                                .padding(innerPadding),
                                onClick = {
                                    navController.navigate(route = "SignInScreen") {
                                        popUpTo("SplashScreen") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable<Route.SignInScreen> {
                            SignInScreen(
                                modifier = Modifier
                                .padding(innerPadding),
                                onClick = {
                                    navController.navigate(route = "SignUpScreen") {
                                    }
                                }
                            )
                        }
                        composable<Route.SignUpScreen> {
                            SignUpScreen(
                                modifier = Modifier
                                    .padding(innerPadding),
                                onClick = {
                                    navController.navigate(route = "SignInScreen")
                                }
                            )
                        }
                    }
 */