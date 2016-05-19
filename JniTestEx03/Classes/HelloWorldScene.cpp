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

#ifdef __cplusplus
extern "C" {
#endif

	void Java_org_cocos2dx_cpp_AppActivity_addSpriteInNative(JNIEnv *enf, jobject obj)
	{
		g_pHello->addSpriteFromJava();
	}
	void Java_org_cocos2dx_cpp_AppActivity_removeSpriteInNative(JNIEnv *env, jobject obj)
	{
		g_pHello->removeSpriteFromJava();
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
		"Add Sprite",
		CC_CALLBACK_1(HelloWorld::addSprite, this));
	pMenuItem1->setColor(Color3B(0, 0, 0));
	
	auto pMenuItem2 = MenuItemFont::create(
		"Remove Sprite",
		CC_CALLBACK_1(HelloWorld::removeSprite, this));
	pMenuItem2->setColor(Color3B(0, 0, 0));

	auto pMenu = Menu::create(pMenuItem1, pMenuItem2, nullptr);
	pMenu->alignItemsVerticallyWithPadding(50.0f);

	this->addChild(pMenu);

	///////////////////////////
	g_pHello = this;
	///////////////////////////

	return true;
}

void HelloWorld::addSprite(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	callJavaMethod("AddSprite");
#endif
}

void HelloWorld::removeSprite(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	callJavaMethod("RemoveSprite");
#endif
}

void HelloWorld::addSpriteFromJava()
{
	auto sprite = Sprite::create("Images/HelloWorld.png");
	sprite->setPosition(Vec2(240, 160));
	sprite->setTag(1);
	this->addChild(sprite);
}

void HelloWorld::removeSpriteFromJava()
{
	auto sprite = (Sprite*)this->getChildByTag(1);
	sprite->removeFromParent();
}

