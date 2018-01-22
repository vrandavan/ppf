package com.ppsf.elementfactory;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.reflect.Field;
import java.util.List;

/**
* The default element locator, which will lazily locate an element or an element list on a page. This class is
* designed for use with the {@link org.openqa.selenium.support.PageFactory} and understands the
* annotations {@link org.openqa.selenium.support.FindBy} and {@link org.openqa.selenium.support.CacheLookup}.
*/
public class PPSFElementLocator implements ElementLocator {
private final SearchContext searchContext;
private final boolean shouldCache;
private final By by;
private WebElement cachedElement;
private List<WebElement> cachedElementList;
public String pageClass;
public String objName;
/**
* Creates a new element locator.
*
* @param searchContext The context to use when finding the element
* @param field The field on the Page Object that will hold the located value
*/
/*public DefaultElementLocator(SearchContext searchContext, Field field) {
 this(searchContext, new Annotations(field));
}
*/
public PPSFElementLocator(SearchContext searchContext, Field field, Object pageClass) {
	 this(searchContext, new Annotations(field));
	 this.pageClass = pageClass.getClass().getSimpleName();
	 this.objName=field.getName();
	}
/**
* Use this constructor in order to process custom annotaions.
*
* @param searchContext The context to use when finding the element
* @param annotations AbstractAnnotations class implementation
*/
public PPSFElementLocator(SearchContext searchContext, AbstractAnnotations annotations) {
 this.searchContext = searchContext;
 this.shouldCache = annotations.isLookupCached();
 this.by = annotations.buildBy();
}

/**
* Find the element.
*/
public WebElement findElement() {
 if (cachedElement != null && shouldCache) {
   return cachedElement;
 }

 WebElement element = searchContext.findElement(by);
 if (shouldCache) {
   cachedElement = element;
 }

 return element;
}

/**
* Find the element list.
*/
public List<WebElement> findElements() {
 if (cachedElementList != null && shouldCache) {
   return cachedElementList;
 }

 List<WebElement> elements = searchContext.findElements(by);
 if (shouldCache) {
   cachedElementList = elements;
 }

 return elements;
}

@Override
public String toString(){
 //return this.getClass().getSimpleName() + " '" + by + "'";
	return "Element: "+this.objName+ " in page "+this.pageClass+ " with locator '"+ by + "'";
}
}
