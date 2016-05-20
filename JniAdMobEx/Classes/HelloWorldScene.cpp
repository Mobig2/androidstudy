#include "HelloWorldScene.h"

static HelloWorld *g_pHello = NULL;
USING_NS_CC;

#if (CC_TARGET_PLATFORM	 == CC_PLATFORM_ANDROID)
#include "platform\android\jni\JniHelper.h"

void callJavaMethod(std::string func)
{
	JniMethodInfo t;
	/*
	JniHelper를 통해 org/cocos2dx/cpp/에 있는
	AppActivity class의 파라미터로 들어온 스트링 이름의 함수 정보를 가져온다.
	*/
	if (JniHelper::getStaticMethodInfo(t
		, "org.cocos2dx.cpp.AppActivity"
		, func.c_str()
		, "()V"))
	{
		//함수 호출
		t.env->CallStaticVoidMethod(t.classID, t.methodID);
		//Release
		t.env->DeleteLocalRef(t.classID);
	}
}
#else
//iOS 개발시 ObjectC에서는 헤더만 추가하면된다.
//#include "Util/Admob/LayerAdmob.h" 
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
	MenuItemFont::setFontSize(40);
	auto pMenuItem1 = MenuItemFont::create(
		"Show",
		CC_CALLBACK_1(HelloWorld::doShow, this));
	pMenuItem1->setColor(Color3B(0, 0, 0));

	auto pMenuItem2 = MenuItemFont::create(
		"Hide",
		CC_CALLBACK_1(HelloWorld::doHide, this));
	pMenuItem2->setColor(Color3B(0, 0, 0));

	auto pMenuItem3 = MenuItemFont::create(
		"FullShow",
		CC_CALLBACK_1(HelloWorld::doFullShow, this));
	pMenuItem3->setColor(Color3B(0, 0, 0));


	auto pMenu = Menu::create(pMenuItem1, pMenuItem2, pMenuItem3, nullptr);
	pMenu->alignItemsHorizontally();

	this->addChild(pMenu);
	
	return true;
}

void HelloWorld::doShow(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	callJavaMethod("ShowAdPopup");
//	ShowAdmobAds();
#endif
}

void HelloWorld::doHide(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	callJavaMethod("HideAdPopup");
//	HideAdmobAds();
#endif
}

void HelloWorld::doFullShow(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	callJavaMethod("ShowAdFull");
#else
//	ShowAdmobFullAds();
#endif
}
