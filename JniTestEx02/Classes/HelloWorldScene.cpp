#include "HelloWorldScene.h"

USING_NS_CC;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "platform\android\jni\JniHelper.h"

#ifdef __cplusplus
extern "C" {
#endif

	jint Java_org_cocos2dx_cpp_AppActivity_cppSum(JNIEnv *env, jobject obj, jint a, jint b)
	{
		return a + b;
	}
#ifdef __cplusplus
}
#endif // __cplusplus

#endif // (CC_TARGET_PLATFORMTFORM == CC_PLATFORM_ANDROID)



Scene* HelloWorld::createScene()
{
	auto scene = Scene::create();
	auto layer = HelloWorld::create();
	scene->addChild(layer);

	return scene;
}

bool HelloWorld::init()
{
	if (!LayerColor::initWithColor(Color4B(255, 255, 255, 255)))
	{
		return false;
	}

	/////////////////////////////
	MenuItemFont::setFontName("fonts/Marker Felt.ttf");
	auto pMenuItem1 = MenuItemFont::create(
		"Java Method Call",
		CC_CALLBACK_1(HelloWorld::callJavaMethod, this));
	
	pMenuItem1->setColor(Color3B(0, 0, 0));

	// 메뉴 생성
	auto pMenu = Menu::create(pMenuItem1, nullptr);

	// 레이어에 메뉴 객체 추가
	this->addChild(pMenu);

	return true;
}

void HelloWorld::callJavaMethod(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo t;
	/*
	JniHelper를 통해 org/cocos2dx/application/ or org.cocos2dx.cpp.AppActivity에 있는
	AppActivity class의 JniTestFunc함수 정보를 가져온다.
	*/
	if (JniHelper::getStaticMethodInfo(t
		, "org.cocos2dx.cpp.AppActivity"
		, "JniTestFunc"
		, "()V"))
	{
		//함수 호출
		t.env->CallStaticVoidMethod(t.classID, t.methodID);
		//Release
		t.env->DeleteLocalRef(t.classID);
	}
#endif // (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)


}
