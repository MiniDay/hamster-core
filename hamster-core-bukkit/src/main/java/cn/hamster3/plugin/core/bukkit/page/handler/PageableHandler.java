package cn.hamster3.plugin.core.bukkit.page.handler;

import cn.hamster3.plugin.core.bukkit.page.ButtonGroup;
import cn.hamster3.plugin.core.bukkit.page.PageConfig;
import cn.hamster3.plugin.core.bukkit.page.PageElement;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 支持翻页的 GUI
 *
 * @param <E> 页面元素
 */
@SuppressWarnings("unused")
public abstract class PageableHandler<E extends PageElement> extends FixedPageHandler {
    private String previewButtonName = "preview";
    private String nextButtonName = "next";
    private String barrierButtonName = "barrier";
    private String elementButtonName = "element";

    private int page;
    private HashMap<Integer, E> elementSlot;

    public PageableHandler(@NotNull HumanEntity player, int page) {
        super(player);
        this.page = page;
    }

    public PageableHandler(@NotNull HumanEntity player, @NotNull String title, int page) {
        super(player, title);
        this.page = page;
    }

    public PageableHandler(@NotNull PageConfig pageConfig, @NotNull HumanEntity player, int page) {
        super(pageConfig, player);
        this.page = page;
    }

    public PageableHandler(@NotNull PageConfig pageConfig, @NotNull String title, @NotNull HumanEntity player, int page) {
        super(pageConfig, title, player);
        this.page = page;
    }

    @NotNull
    public abstract List<E> getPageElements();

    public abstract void onClickElement(@NotNull ClickType clickType, @NotNull InventoryAction action, @NotNull E element);

    @NotNull
    public String getElementButtonName(@NotNull E element) {
        return elementButtonName;
    }

    public void initElementButton(@NotNull E element, @NotNull ItemStack displayItem, HashMap<String, String> replacer) {
        element.replaceItemInfo(getPlayer(), displayItem, replacer);
    }

    @Override
    public void initPage() {
        super.initPage();
        List<E> elements = getPageElements();
        ButtonGroup group = getButtonGroup();
        Inventory inventory = getInventory();
        HumanEntity player = getPlayer();

        ArrayList<Integer> buttonIndexes = group.getButtonAllIndex(elementButtonName);
        int pageSize = buttonIndexes.size(); // 一页有多少个按钮
        elementSlot = new HashMap<>();

        HashMap<String, String> replacer = getReplacer();

        for (int i = 0; i < pageSize; i++) {
            // 元素在当前 page 中的索引位置
            int elementIndex = page * pageSize + i;
            // 按钮在 GUI 中的索引位置
            int buttonIndex = buttonIndexes.get(i);

            if (elementIndex >= elements.size()) {
                inventory.setItem(buttonIndex, null);
                continue;
            }

            E element = elements.get(elementIndex);
            elementSlot.put(buttonIndex, element);

            ItemStack elementDisplayItem = element.getDisplayItem(player);
            if (elementDisplayItem != null) {
                elementDisplayItem = elementDisplayItem.clone();
                element.replaceItemInfo(player, elementDisplayItem, replacer);
                inventory.setItem(buttonIndex, elementDisplayItem);
                continue;
            }

            ItemStack button = group.getButton(getElementButtonName(element));
            if (button == null) {
                inventory.setItem(buttonIndex, null);
                continue;
            }

            ItemStack elementItem = button.clone();
            initElementButton(element, elementItem, replacer);
            inventory.setItem(buttonIndex, elementItem);
        }

        if (page == 0) {
            // 如果页面已在首页则撤掉上一页按钮
            inventory.setItem(group.getButtonIndex(previewButtonName), group.getButton(barrierButtonName));
        }
        if (elements.size() <= (page + 1) * pageSize) {
            // 如果页面显示超出已有元素数量则撤掉下一页按钮
            inventory.setItem(group.getButtonIndex(nextButtonName), group.getButton(barrierButtonName));
        }
    }

    @Override
    public void onClickInside(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        E e = elementSlot.get(slot);
        if (e != null) {
            onClickElement(event.getClick(), event.getAction(), e);
            return;
        }
        String name = getConfig().getButtonName(event.getCurrentItem());
        if (name.equalsIgnoreCase(nextButtonName)) {
            showNextPage();
            return;
        }
        if (name.equalsIgnoreCase(previewButtonName)) {
            showPreviewPage();
        }
    }

    public void showPreviewPage() {
        page--;
        show();
    }

    public void showNextPage() {
        page++;
        show();
    }

    @NotNull
    public HashMap<Integer, E> getElementSlot() {
        return elementSlot;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPreviewButtonName(String previewButtonName) {
        this.previewButtonName = previewButtonName;
    }

    public void setNextButtonName(String nextButtonName) {
        this.nextButtonName = nextButtonName;
    }

    public void setBarrierButtonName(String barrierButtonName) {
        this.barrierButtonName = barrierButtonName;
    }

    public void setElementButtonName(String elementButtonName) {
        this.elementButtonName = elementButtonName;
    }

    public HashMap<String, String> getReplacer() {
        return new HashMap<>();
    }
}
